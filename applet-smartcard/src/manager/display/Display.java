package manager.display;

import manager.transport.SubCommand;

/** ������ ��������� �������� �� ��������� ������, ������� �������� ����������������� ���������� <br>
 * ������ ������ - ����������� ���������� ������
 */
public interface Display {
	/** �����, ������� �������� �� ��������� ������� sub_command ��� ����������� ������������ */
	public void doSubCommand(SubCommand sub_command);
}
