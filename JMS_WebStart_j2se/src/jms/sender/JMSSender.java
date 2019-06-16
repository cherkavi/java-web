package jms.sender;

import java.util.ArrayList;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/** отвечает за отправку текстовых сообщений через JMS */
public class JMSSender extends Thread{
	/** текстовые сообщени€ дл€ отправки на сервер  */
	private ArrayList<String> messages=new ArrayList<String>();
	private ActiveMQConnectionFactory  connectionFactory = null;
	/** полное наименование очереди */
	private String queueName;
	/** максимальный размер очереди */
	private int maxSize;
	
	/** отвечает за отправку текстовых сообщений через JMS 
	 * <br />
	 * @param pathToJMS полный путь к JMS "tcp://192.168.15.120:61616"
	 * @param queueName полное им€ очереди сообщени€
	 * Ќеобходим ќЅя«ј“≈Ћ№Ќџ… старт методом start()
	 * */
	public JMSSender(String pathToJMS, String queueName, int maxQueueSize){
		this.queueName=queueName;
		this.maxSize=maxQueueSize;
		// Class.forName("org.activemq.jndi.ActiveMQInitialContextFactory");
		// (ActiveMQConnectionFactory) context.lookup("ActiveMQConnectionFactory");   
		this.connectionFactory =new ActiveMQConnectionFactory(pathToJMS);
	}
	
	private volatile boolean flagRun=true;
	public void stopThread(){
		this.flagRun=false;
		this.interrupt();
	}
	
	/** объект, который будет оповещатьс€ о необходимости просмотра сообщений дл€ отправки на сервер */
	private Object signal=new Object();
	
	
	public void run(){
		while(this.flagRun){
			if(this.messages.size()>0){
				// есть данные дл€ отправки
				String messageForSend=null;
				synchronized(this.messages){
					messageForSend=this.messages.remove(0);
				}
				if(messageForSend!=null){
					// есть сообщение дл€ отправки
					if(sendMessage(messageForSend)==true){
						debug("сообщение успешно отправлено"); 
					}else{
						// вернуть сообщение в очередь - ошибка отправки 
						synchronized(this.messages){
							this.messages.add(0, messageForSend);
						};
					}
				}else{
					// нечего отправл€ть - сообщение is null 
				}
			}else{
				// нет данных дл€ отправки 
				synchronized(this.signal){
					if(this.messages.size()>0){
						// задача по€вилась, пока устанавливалась блокировка
						continue;
					}else{
						try{
							this.signal.wait();
						}catch(InterruptedException ex){};
					}
				}
			}
			
		}
	}
	
	/** добавит текстовое сообщение в очередь 
	 * @param textMessage - текстовое сообщение
	 * @return 
	 * <ul>
	 * 	<li><b>true</b> - сообщение успешно добавлено </li>
	 * 	<li><b>false</b> - сообщение не добавлено - превышение лимита сообщений дл€ отправки (отправка не идет)</li>
	 * </ul>
	 * */
	public boolean addTextMessage(String textMessage){
		synchronized(this.messages){
			if(this.messages.size()>=maxSize){
				// превышен лимит сообщений 
				return false;
			}else{
				this.messages.add(textMessage);
			}
		}
		synchronized(this.signal){
			this.signal.notify();
		}
		return true;
	}
	
	/** отправить сообщение на JMS */
	private boolean sendMessage(String messageText){
		boolean returnValue=false;
		Connection connection=null;
		try{
			// context=new InitialContext(props);
			connection = connectionFactory.createConnection();
		    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		    Queue queue=session.createQueue(this.queueName);
			MessageProducer producer=session.createProducer(queue);
			connection.start();
			TextMessage message=session.createTextMessage(messageText);
			producer.send(message);
			connection.close();
			// сообщение успешно послано
			return true;
		}catch(Exception ex){
			// отправка сообщени€ прошла не успешно
			error("#sendMessage Exception:"+ex.getMessage());
			returnValue=false;
		}
		return returnValue;
	}
	
	/** вывод отладочных сообщений */
	private void debug(Object message){
		System.out.println("DEBUG JMSSender#"+message);
	}
	
	/** вывод ошибочных сообщений */
	private void error(Object message){
		System.out.println("ERROR JMSSender#"+message);
	}
}
