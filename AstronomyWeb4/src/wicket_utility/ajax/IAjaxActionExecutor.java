package wicket_utility.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;

/** ���������� � �������� */
public interface IAjaxActionExecutor {
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
	 * @return ��������� �������� ������� ������������� �������� 
	 * <ul>
	 * 	<li> <b>0</b> - �������� ���������� </li>
	 * 	<li> <b>&gt0</b> - ������ ����������� </li>
	 * </ul>
	 */
	public int action(AjaxRequestTarget target, 
					   String name, 
					   Object ... parameters);
}
