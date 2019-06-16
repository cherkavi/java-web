import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class JPanelMenu extends JPanel{
	JTextField field_text_url;
	JTextField field_text_for_send;
	JButton field_button_send;
	JTextArea field_log;
	public JPanelMenu(){
		super();
		initComponents();
	}
	
	private void initComponents(){
		// create components
		field_text_url=new JTextField("http://localhost:8080/Servlet_Applet_exchange_Servlet/TransportServlet");
		field_text_for_send=new JTextField();
		field_button_send=new JButton("Send data");
		field_log=new JTextArea();
		// add listener's
		field_button_send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				on_button_send_click();
			}
		});
		// placing components
		JPanel panel_url=this.wrapComponentToTitlePanel(field_text_url, "URL");
		JPanel panel_for_send=this.wrapComponentToTitlePanel(field_text_for_send, "Text for send");
		JPanel panel_log=this.wrapComponentToTitlePanel(field_log, "Полученные данные");
		JPanel panel_manager=new JPanel(new GridLayout(3,1));
		panel_manager.add(panel_url);
		panel_manager.add(panel_for_send);
		panel_manager.add(field_button_send);
		panel_manager.add(panel_log);
		this.setLayout(new BorderLayout());
		this.add(panel_manager,BorderLayout.NORTH);
		this.add(panel_log,BorderLayout.CENTER);
	}
	
	private JPanel wrapComponentToTitlePanel(JComponent component,String title){
		JPanel return_value=new JPanel(new GridLayout(1,1));
		return_value.add(component);
		return_value.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		return return_value;
	}
	
	/** 
	 * объект, который выводит пользователю полученный ответ от сервлета
	 */
	private void showAnswer(Transport transport){
		this.field_log.append(transport.getCommand());
		this.field_log.append("\n");
	}
	/**  передача параметров на сервер, и ожидание ответа от сервера
	 * 
	 */
	private void on_button_send_click(){
		Transport send_object=new Transport(this.field_text_for_send.getText());
		Transport answer_object=Sender.sendTransoportGetAnswer(this.field_text_url.getText(),send_object);
		this.showAnswer(answer_object);
	}
}
