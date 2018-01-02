package gui.EnterPoint;
import javax.swing.*;

/**
 * класс, который служит точкой входа в SE приложение - главный Фрейм 
 * @author cherkashinv
 */
@SuppressWarnings("serial")
public class JFrameMain extends JFrame{
	/** заголовок для формы*/
	private static String field_caption="Connection with Smart Card";
	/** рабочий стол, на котором будут помещены все внутренние фреймы*/
	private JDesktopPane field_desktop;
	
	
	public static void main(String args[]){
		new JFrameMain(field_caption);
	}
	
	public JFrameMain(String caption){
		super(caption);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800,600);
		this.field_desktop=new JDesktopPane();
		this.getContentPane().add(this.field_desktop);
		this.setVisible(true);
		//Position.set_frame_to_center(this);
		new JInternalFrameMain(this.field_desktop,"Main frame", 750,550);
	}
	
}
