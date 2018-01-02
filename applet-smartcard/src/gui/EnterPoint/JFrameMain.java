package gui.EnterPoint;
import javax.swing.*;

/**
 * �����, ������� ������ ������ ����� � SE ���������� - ������� ����� 
 * @author cherkashinv
 */
@SuppressWarnings("serial")
public class JFrameMain extends JFrame{
	/** ��������� ��� �����*/
	private static String field_caption="Connection with Smart Card";
	/** ������� ����, �� ������� ����� �������� ��� ���������� ������*/
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
