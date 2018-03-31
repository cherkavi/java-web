package manager.display;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import manager.transport.SubCommand;

/** �������, ������� �������� �� �������������� ����� � �������������, ����������� JFrame ��� JInternalFrame */
public class JInternalFrameDisplay implements Display{
	private Component field_internal_frame;
	private JTextArea field_area_for_log;
	@Override
	public void doSubCommand(SubCommand sub_command) {
		if(sub_command.getCommand().equalsIgnoreCase("SHOWMESSAGE")){
			try{
				JOptionPane.showMessageDialog(field_internal_frame, sub_command.getParameterString());
				sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
			}catch(Exception ex){
				// ������ �� ���������� - �������� ������ � DATA=null, � ������ ��������� - DATA_ORIGINAL
				sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
			}
		};
		if(sub_command.getCommand().equalsIgnoreCase("WRITETOLOG")){
			try{
				this.field_area_for_log.append(sub_command.getParameterString());
				this.field_area_for_log.append("\n");
				sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
			}catch(Exception ex){
				// ������ �� ���������� - �������� ������ � DATA=null, � ������ ��������� - DATA_ORIGINAL
				sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
			}
		};
		
	}
	
	/**
	 * �������, ������� �������� �� �������������� ����� � �������������, ����������� JFrame ��� JInternalFrame
	 * @param internal_frame
	 */
	public JInternalFrameDisplay(Component internal_frame,JTextArea area_for_log){
		this.field_internal_frame=internal_frame;
		this.field_area_for_log=area_for_log;
	}
	
	
	
	
}
