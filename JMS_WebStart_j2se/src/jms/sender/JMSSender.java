package jms.sender;

import java.util.ArrayList;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/** �������� �� �������� ��������� ��������� ����� JMS */
public class JMSSender extends Thread{
	/** ��������� ��������� ��� �������� �� ������  */
	private ArrayList<String> messages=new ArrayList<String>();
	private ActiveMQConnectionFactory  connectionFactory = null;
	/** ������ ������������ ������� */
	private String queueName;
	/** ������������ ������ ������� */
	private int maxSize;
	
	/** �������� �� �������� ��������� ��������� ����� JMS 
	 * <br />
	 * @param pathToJMS ������ ���� � JMS "tcp://192.168.15.120:61616"
	 * @param queueName ������ ��� ������� ���������
	 * ��������� ������������ ����� ������� start()
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
	
	/** ������, ������� ����� ����������� � ������������� ��������� ��������� ��� �������� �� ������ */
	private Object signal=new Object();
	
	
	public void run(){
		while(this.flagRun){
			if(this.messages.size()>0){
				// ���� ������ ��� ��������
				String messageForSend=null;
				synchronized(this.messages){
					messageForSend=this.messages.remove(0);
				}
				if(messageForSend!=null){
					// ���� ��������� ��� ��������
					if(sendMessage(messageForSend)==true){
						debug("��������� ������� ����������"); 
					}else{
						// ������� ��������� � ������� - ������ �������� 
						synchronized(this.messages){
							this.messages.add(0, messageForSend);
						};
					}
				}else{
					// ������ ���������� - ��������� is null 
				}
			}else{
				// ��� ������ ��� �������� 
				synchronized(this.signal){
					if(this.messages.size()>0){
						// ������ ���������, ���� ��������������� ����������
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
	
	/** ������� ��������� ��������� � ������� 
	 * @param textMessage - ��������� ���������
	 * @return 
	 * <ul>
	 * 	<li><b>true</b> - ��������� ������� ��������� </li>
	 * 	<li><b>false</b> - ��������� �� ��������� - ���������� ������ ��������� ��� �������� (�������� �� ����)</li>
	 * </ul>
	 * */
	public boolean addTextMessage(String textMessage){
		synchronized(this.messages){
			if(this.messages.size()>=maxSize){
				// �������� ����� ��������� 
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
	
	/** ��������� ��������� �� JMS */
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
			// ��������� ������� �������
			return true;
		}catch(Exception ex){
			// �������� ��������� ������ �� �������
			error("#sendMessage Exception:"+ex.getMessage());
			returnValue=false;
		}
		return returnValue;
	}
	
	/** ����� ���������� ��������� */
	private void debug(Object message){
		System.out.println("DEBUG JMSSender#"+message);
	}
	
	/** ����� ��������� ��������� */
	private void error(Object message){
		System.out.println("ERROR JMSSender#"+message);
	}
}
