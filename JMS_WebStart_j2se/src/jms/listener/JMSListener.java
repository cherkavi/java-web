package jms.listener;
import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSListener implements MessageListener{
	private ActiveMQConnectionFactory connectionFactory;
	private String queueName;
	private ITextDataRecieve textDataRecieve;

	public JMSListener(String jmsPath, String queueName, ITextDataRecieve textDataRecieve){
		this.queueName=queueName;
		this.textDataRecieve=textDataRecieve;
		connectionFactory =new ActiveMQConnectionFactory(jmsPath); // (ActiveMQConnectionFactory) context.lookup("ActiveMQConnectionFactory");
		initListener();
	}
	
	public void initListener(){
		try{
			Connection connection = connectionFactory.createConnection();
		    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		    Queue queue=session.createQueue(this.queueName);
		    
		    MessageConsumer consumer=session.createConsumer(queue);
		    
		    consumer.setMessageListener(this);
			connection.start();
		}catch(Exception ex){
			error("#initListener Exception:"+ex.getMessage());
		}
	}
	
	private void error(Object message){
		System.err.println("ERROR JMSListener#"+message);
	}

	@Override
	public void onMessage(Message message) {
		try{
			if(message instanceof TextMessage){
				this.textDataRecieve.recieveTextData(((TextMessage)message).getText());
			}else{
				if(message!=null){
					error("#onMessage was recieved not TextMessage "+message.getJMSType());
				}else{
					error("#onMessage was recieved null ");
				}
			}
		}catch(Exception ex){
			error("#onMessage Exception "+ex.getMessage());
		}
	}
}
