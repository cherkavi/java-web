package database_reflect;

import java.sql.Connection;

import database_reflect.database.ConnectWrap;
import database_reflect.database.IConnectorAware;
import database_reflect.finder.RecordFinder;
import database_reflect.sender.Sender;
import database_reflect.wrapper.wrap.*;

/** �����, ������� �������� �� ����� ������� � �� �������� �� ������ */
public class ReflectWorker implements Runnable{
	/** ��������� ���������� � ����� ������ */
	private IConnectorAware connectorAware;
	/** �������� ����� �������� ��������� ������ ������ */
	private int delaySend;
	/** �������� � ��. ����� �������� ��������� ������� ������*/
	private int delayError;
	/** ����, ������� "�������" � ������������� ����������� ���������� ������ */
	private boolean isRun=false;
	/** ������, ������� ���������� ����� ������� � ���� ������ � ���������� ��������� �� ������  */
	private RecordFinder recordFinder=new RecordFinder(new WrapCustomer(), new WrapCartridgeModel(), new WrapCartridgeVendor(), new WrapOrderGroup(), new WrapOrderList());
	/** ������, ������� ���������� ������ �� ������ */
	private Sender sender=null;
	/** ������, ������� ����� �������������� � �������� ������������ �������, ��� ��������� */
	private Object controlObject=new Object();
	/** 
	 * ������, ������� �������� �� ����� �������, �������� �� ������ ��������� ������� � ���������� ��������
	 * @param pathToServer - ���� � �������, ������� ������������� ������ "http://localhost:8080/DatabaseReflectServer"
	 * @param connectionAware - ������, ������� ������� ������������ � ����� ������ 
	 * @param delaySend - �������� (��.) ����� ��������� ��������� 
	 * @param delayError - �������� (��.) ����� ��������� �������� ��������, � ������ ������� 
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
								// ������ ����� � ��������
								try{
									Thread.sleep(this.delayError);
								}catch(Exception ex){};
								System.err.println("ReflectWorker#run Server does not response");
								continue try_again;
							}else{
								if(result.equals("OK")){
									// ������ ������� ��������
									if(recordFinder.lastRecordSetAsSended(connection)){
										// ������ ������� �������� ��� ���������
									}else{
										// ������ ������� ������ ��� ��������� 
										System.err.println("ReflectWorker#run data send Error");
									}
									// break;
								}else{
									// ������ �������� ������ �� ������
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
				// �������� ��� ��������� ��������
				try{
					Thread.sleep(this.delaySend);
				}catch(Exception ex){};
			}// end main_cycle
			// �������� �� ����������� �������� 
			synchronized(controlObject){
				objectForSend=this.getNextObjectFromDatabase();
				if((objectForSend!=null)||(isRun==true)){
					// ������ ������� ����
					continue; 
				}else{
					// ����� �� �������� ����� � ��������� �����
					break;
				}
			}
		}
	}
	
	/** ��������� ������� ����, ���� �� ��� �� ������� */
	public void runProcess(){
		synchronized(controlObject){
			if(isRun==false){
				// ���������� ��������� ����� 
				(new Thread(this)).run();
			}else{
				// ����� �������
			}
		}
	}
	
	/** �������� ��������� ������ �� ���� ������ */
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
