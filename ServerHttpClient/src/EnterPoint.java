import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.*;

import transport.Transport;

public class EnterPoint extends JFrame{
	private final static long serialVersionUID=1L;
	
	private void debug(Object information){
		System.out.print("EnterPoint");
		System.out.print(" DEBUG ");
		System.out.println(information);
	}

	private void error(Object information){
		System.out.print("EnterPoint");
		System.out.print(" ERROR ");
		System.out.println(information);
	}
	
	public static void main(String[] args){
		new EnterPoint();
	}
	
	public EnterPoint(){
		super("Client example");
		this.initComponents();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(200,150);
		this.setVisible(true);
	}
	
	private void initComponents(){
		JPanel panelMain=new JPanel();
		panelMain.setLayout(new BorderLayout());
		JButton buttonSend=new JButton("Send");
		buttonSend.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonSend();
			}
		});
		panelMain.add(buttonSend,BorderLayout.NORTH);
		this.getContentPane().add(panelMain);
	}
	
	private void onButtonSend(){
		String urlPath="http://localhost:8080/ServerHttp/main";
		try{
			URLConnection connection=(new URL(urlPath)).openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.connect();
			
			// INFO create Transport object
			Transport transport=new Transport("Client transport");
			transport.setInteger(new Integer(20));
			transport.setString("This is string for client");
			
			// INFO read data from URL
			Transport response=sendToServerGetResponse(transport,connection);
			
			debug("This is response:"+response);
		}catch(Exception ex){
			error("onButtonSend:"+ex.getMessage());
		}
	}
	
	/** послать на сервер Transport и получить ответ от него */
	private Transport sendToServerGetResponse(Transport forSend, URLConnection connection) throws IOException{
		writeObjectToOutputStream(forSend,connection.getOutputStream());
		connection.getOutputStream().close();
		return readObjectFromInputStream(connection.getInputStream());
	}
	
    /** записать/сериализовать объект, в OutputStream */
    private void writeObjectToOutputStream(Object object, OutputStream stream) throws IOException{
    	ObjectOutputStream oos=new ObjectOutputStream(stream);
    	oos.writeObject(object);
    }
	
    /** прочитать из InputStream объект */
	private Transport readObjectFromInputStream(InputStream stream){
    	Transport returnValue=null;
    	try{
    		ObjectInputStream is=new ObjectInputStream(stream);
    		returnValue=(Transport)is.readObject();
    	}catch(Exception ex){
    		error("readObjectFromInputStream: "+ex.getMessage());
    	}
    	return returnValue;
    }
}
