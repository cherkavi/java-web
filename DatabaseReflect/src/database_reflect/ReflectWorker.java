package database_reflect;

import java.sql.Connection;

import database_reflect.database.ConnectWrap;
import database_reflect.database.IConnectorAware;
import database_reflect.finder.RecordFinder;
import database_reflect.sender.Sender;
import database_reflect.wrapper.wrap.*;

/** класс, который отвечает за поиск записей и их отправку на сервер */
public class ReflectWorker implements Runnable{
	/** генератор соединений с базой данных */
	private IConnectorAware connectorAware;
	/** задержка перед посылкой очередной порции данных */
	private int delaySend;
	/** задержка в мс. перед попыткой следующей посылки данных*/
	private int delayError;
	/** флаг, который "говорит" о необходимости продолжени€ выполнени€ работы */
	private boolean isRun=false;
	/** объект, который производит поиск записей в базе данных и отправл€ет найденные на сервер  */
	private RecordFinder recordFinder=new RecordFinder(new WrapCustomer(), new WrapCartridgeModel(), new WrapCartridgeVendor(), new WrapOrderGroup(), new WrapOrderList());
	/** объект, который отправл€ет данные на сервер */
	private Sender sender=null;
	/** объект, который будет использоватьс€ в качестве захваченного ресурса, дл€ вы€влени€ */
	private Object controlObject=new Object();
	/** 
	 * ќбъект, который отвечает за поиск записей, отправку на сервер найденных записей и уменьшение счетчика
	 * @param pathToServer - путь к серверу, который предоставл€ет сервис "http://localhost:8080/DatabaseReflectServer"
	 * @param connectionAware - объект, который владеет соединени€ми с базой данных 
	 * @param delaySend - задержка (мс.) между следующей отправкой 
	 * @param delayError - задержка (мс.) между следующей попыткой отправки, в случае неудачи 
	 */
	public ReflectWorker(String pathToServer, IConnectorAware connectionAware, int delaySend, int delayError){
		this.connectorAware=connectionAware;
		this.delaySend=delaySend;
		this.delayError=delayError;
		sender=new Sender(pathToServer);
		this.isRun=true;
		(new Thread(this)).run();
	}
	
	@Override
	public void run() {
		Object objectForSend=null;
		while(true){
			main_cycle: while(isRun){
				ConnectWrap connector=this.connectorAware.getConnector();
				try{
					Connection connection=connector.getConnection();
					objectForSend=recordFinder.getNextFindRecord(connection);
					if(objectForSend==null){
						// no data for send - exit
						break main_cycle;
					}else{
						String result=null;
						// data exists - send it
						try_again:while(true){
							result=sender.sendData(objectForSend);
							if(result==null){
								// ошибка св€зи с сервером
								try{
									Thread.sleep(this.delayError);
								}catch(Exception ex){};
								System.err.println("ReflectWorker#run Server does not response");
								continue try_again;
							}else{
								if(result.equals("OK")){
									// данные успешно переданы
									if(recordFinder.lastRecordSetAsSended(connection)){
										// данные успешно помечены как посланные
									}else{
										// ошибка пометки данных как посланных 
										System.err.println("ReflectWorker#run data send Error");
									}
									// break;
								}else{
									// ошибка передачи данных на сервер
									try{
										Thread.sleep(this.delayError);
									}catch(Exception ex){};
									System.err.println("ReflectWorker#run Server responsed error");
									continue try_again;
								}
							}
							break try_again;
						}
					}
				}catch(Exception ex){
					System.err.println("ReflectWorker#run: Exception:"+ex.getMessage());
				}finally{
					connector.close();
				}
				// задержка дл€ следующей итерации
				try{
					Thread.sleep(this.delaySend);
				}catch(Exception ex){};
			}// end main_cycle
			// проверка на продолжение итерации 
			synchronized(controlObject){
				objectForSend=this.getNextObjectFromDatabase();
				if((objectForSend!=null)||(isRun==true)){
					// начать рабочий цикл
					continue; 
				}else{
					// выйти из рабочего цикла и завершить поток
					break;
				}
			}
		}
	}
	
	/** запустить рабочий цикл, если он еще на зупущен */
	public void runProcess(){
		synchronized(controlObject){
			if(isRun==false){
				// необходимо запустить поток 
				(new Thread(this)).run();
			}else{
				// поток запущен
			}
		}
	}
	
	/** получить очередной объект из базы данных */
	private Object getNextObjectFromDatabase(){
		Object returnValue=null;
		ConnectWrap connector=this.connectorAware.getConnector();
		try{
			returnValue=this.recordFinder.getNextFindRecord(connector.getConnection());
		}catch(Exception ex){
			System.err.println("ReflectWorker#run Exception: "+ex.getMessage());
		}finally{
			connector.close();
		}
		return returnValue;
	}

}
