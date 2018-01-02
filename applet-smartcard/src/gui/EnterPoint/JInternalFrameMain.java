package gui.EnterPoint;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;



/**
 * �����, ������� �������� ������� ���������� ������� ��� ����������
 * @author cherkashinv
 */
@SuppressWarnings("serial")
public class JInternalFrameMain extends JInternalFrame implements InternalFrameListener{
	/** ������� ����, �� ������� ���������� ����������� ���� �������*/
	private JDesktopPane field_desktop;

	/**
	 * ������� ���������� �����
	 * @param desktop - ������� ���� ����������� ������
	 * @param caption - ��������� ��� ����������� ������
	 * @param width - ������ ����������� ������
	 * @param height - ������ ����������� ������
	 */
	public JInternalFrameMain(JDesktopPane desktop,
							  String caption,
							  int width,
							  int height){
		super(caption,true,true,true,true);
		this.field_desktop=desktop;
		this.addInternalFrameListener(this);
		this.setSize(width,height);
		Position.set_frame_to_center(this, field_desktop, 20, 20);
		CreateComponents();
		this.field_desktop.add(this);
		this.setVisible(true);
	}

	/** 
	 * ����� ������������ ��� ���������� ����������� ��������� ��� ������ ����� 
	 */
	private void CreateComponents(){
		/** panel for display result connection and log*/
		JPanel panel_display=new JPanel(new GridLayout(1,1));
		JTextArea textarea_for_log=new JTextArea();
		panel_display.add(new JScrollPane(textarea_for_log,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
		/** ������� ������ ��� ���������� �� ��� ����������� ��������� */
		JPanel panel_main=new JPanel();
		
		
		
		// ���������� ����������
		//panel_display.add(new JTextArea());
		
		JSplitPane split_pane=new JSplitPane();
		split_pane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		split_pane.setLeftComponent(new JPanelTestCommand(textarea_for_log));
		split_pane.setRightComponent(panel_display);
		panel_main.setLayout(new GridLayout(1,1));
		panel_main.add(split_pane);
		split_pane.setResizeWeight(0.5);
		this.getContentPane().add(panel_main);
	}
	
	
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		System.exit(0);
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
	}
}
