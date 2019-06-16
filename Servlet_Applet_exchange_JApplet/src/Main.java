import javax.swing.*;


public class Main extends JApplet{
	public void init(){
		super.init();
		// init variables
		JPanelMenu panel_menu=new JPanelMenu();
		this.add(panel_menu);
	}
	public void destroy(){
		super.destroy();
		// destroy variables
	}
	public void Main(){
	}
	public static void main(String[] args){
		JFrame frame_main=new JFrame();
		JPanelMenu panel_menu=new JPanelMenu();
		frame_main.add(panel_menu);
		frame_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_main.setBounds(20,20,400,300);
		frame_main.setVisible(true);
	}
}
