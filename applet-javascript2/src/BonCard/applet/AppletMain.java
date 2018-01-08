package BonCard.applet;
import gui.EnterPoint.JInternalFrameMain;
import gui.EnterPoint.Position;

import javax.swing.JApplet;
import javax.swing.JDesktopPane;

/** 
 * пример апплета, который работает со страницей HTML,
 * общаясь с JavaScript кодом 
 * @author cherkashinv
 */
public class AppletMain extends JApplet implements JavaScriptExchange{
	/** рабочий стол, на котором будут помещены все внутренние фреймы*/
	private JDesktopPane field_desktop;
	/** объект, который занимается обработкой сообщений от JavaScript */
	private JavaScriptExchange field_exchange;

	public void init(){
		super.init();
		// action when applet is init
		System.out.println("init_applet");
		this.initComponents();
	}
	public void destroy(){
		// action when applet is destroy
		System.out.println("destroy_applet");
		super.destroy();
	}

	
	public AppletMain(){
	}
	// ------------------------- методы, которые доступны в JavaScript

	private void initComponents(){
		this.field_desktop=new JDesktopPane();
		this.getContentPane().add(this.field_desktop);
		this.setVisible(true);
		JInternalFrameMain main_frame=new JInternalFrameMain(this,
															 this.field_desktop,
															 "Main frame", 
															 400,
															 300);
		main_frame.setBounds(5,5,main_frame.getWidth(),main_frame.getHeight());
		field_exchange=(JavaScriptExchange)main_frame;
		//Position.set_frame_to_center(main_frame, this.field_desktop);
		System.out.println("constructor:end");
	}
	
	@Override
	public String echo(String value) {
		System.out.println("Call echo:"+value);
		return this.field_exchange.echo(value);
	}

	@Override
	public void method_string(String value) {
		System.out.println("Call method_string:"+value);
		this.field_exchange.method_string(value);
	}

	@Override
	public void method_simple(){
		System.out.println("Call method_simple");
		this.field_exchange.method_simple();
	}
	
}
