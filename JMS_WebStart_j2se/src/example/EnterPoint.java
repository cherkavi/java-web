package example;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import jms.listener.ITextDataRecieve;
import jms.listener.JMSListener;
import jms.sender.JMSSender;

/** визуальный интерфейс для обмена с JMS на сервере  */
public class EnterPoint extends JFrame implements ITextDataRecieve{
	private final static long serialVersionUID=1L;
	private JMSSender sender;
	
	public static void main(String[] args){
		if(args.length>0){
			new EnterPoint(args[0]);
		}
	}
	
	/** визуальный интерфейс для обмена с JMS на сервере  */
	public EnterPoint(String queueName){
		super();
		initComponents();
		String jmsPath="tcp://192.168.15.120:61616";
		System.out.println("Queue: "+queueName);
		// String queueName="text";
		this.sender=new JMSSender(jmsPath,queueName, 10);
		this.sender.start();
		new JMSListener(jmsPath, queueName, this);
		this.setSize(200,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	/** поле для отправки данных  */
	private TextField fieldTextSend;
	/** поле для приема текста от сервера  */
	private TextArea fieldTextRecieve;
	
	private void initComponents(){
		JPanel panelSend=new JPanel(new GridLayout(2,1));
		fieldTextSend=new TextField();
		panelSend.add(fieldTextSend);
		JButton buttonSend=new JButton("Send");
		panelSend.add(buttonSend);
		buttonSend.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				onButtonSend();
			}
		});
		fieldTextRecieve=new TextArea();
		
		Container main=this.getContentPane();
		main.setLayout(new BorderLayout());
		main.add(panelSend, BorderLayout.NORTH);
		main.add(fieldTextRecieve, BorderLayout.CENTER);
		
	}
	
	/** нажата клавиша отправить сообщение  */
	private void onButtonSend(){
		if(sender.addTextMessage(this.fieldTextSend.getText())){
			// сообщение успешно послано
			JOptionPane.showMessageDialog(this, "Ok");
		}else{
			JOptionPane.showMessageDialog(this,"send error","Not Sended",JOptionPane.ERROR_MESSAGE);
		}
		
	}

	@Override
	public void recieveTextData(String remoteTextMessage) {
		final String localString=remoteTextMessage;
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				fieldTextRecieve.setText(localString+"\n"+fieldTextRecieve.getText());
			}
		});
	}
	
}
