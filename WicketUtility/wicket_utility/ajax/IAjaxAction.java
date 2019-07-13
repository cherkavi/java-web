package wicket_utility.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;

/** ���������� � �������� */
public interface IAjaxAction {
	/** �������� ������� ������������� ��������� ���������  */
	public static final int result_ok=(0);
	/** �������� ������� ������������� ��������� ��������� */
	public static final int result_error=(-1);
	/** ������ ����������  */
	public static final String action_show="SHOW";
	
	/** ������ ��������� */
	public static final String action_save="SAVE";
	
	/** ������ �������� */
	public static final String action_cancel="CANCEL";
	
	/** ��������� ���� ������� ������������� ��������� */
	public static final String action_modal_ok="MODAL_OK";
	
	/** ��������� ���� ������� ������������� ��������� */
	public static final String action_modal_cancel="MODAL_CANCEL";
	
	/** ���������� � ��������
	 * @param target - AjaxTarget ��� �������� ������ ���������� ������� 
	 * @param name - ���������� ��� �������� {@link #SHOW}
	 * @param parameters - ���������, ������� ����� �������������
	 */
	public int action(AjaxRequestTarget target, 
					   String name, 
					   Object ... parameters);
}
