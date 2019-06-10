import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;

public class FrameMain extends JApplet {
	
	/** */
	private static final long serialVersionUID = 1L;

	JTextArea field_alert;
	
	private void debug(String information){
		/*System.out.print("FrameMain ");
		System.out.print("DEBUG: ");
		System.out.println(information);
		*/
		field_alert.append("DEBUG: "+information+"\n");
	}
	
	private void error(String information){
		/*System.out.print("FrameMain ");
		System.out.print("ERROR: ");
		System.out.println(information);*/
		field_alert.append("ERROR: "+information+"\n");
	}
	
	public FrameMain(){
		
	}
	
	public void init(){
		initComponents();
	}

	private void initComponents(){
		// create component's
		JButton field_button=new JButton("Call ShowMessage()");
		field_alert=new JTextArea();
		
		// add listener's
		field_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_click();
			}
		});
		// placing component's
		JPanel panel_main=new JPanel(new BorderLayout());
		panel_main.add(field_button,BorderLayout.NORTH);
		panel_main.add(field_alert,BorderLayout.CENTER);
		this.getContentPane().add(panel_main);
	}
	
	/** reaction on striking button */
	private void on_button_click(){
		try{
			debug("try calling JavaScript method: BEGIN ");
			String url="javascript:frame_1.ShowMessage('applet say hello ')";
			debug(url);
			this.getAppletContext().showDocument(new URL(url));
			debug("try calling JavaScript method: END ");
		}catch(Exception ex){
			error("calling JavaScript ERROR "+ex.getMessage());
		};
	}
}
