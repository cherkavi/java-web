package wicket_utility.ajax.table_filter.toolbar.filter.column;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.table_filter.toolbar.filter.AjaxHeadersToolbarFilter;

/** ������ ���������� ��� �������  - ������� ���������� (Caption, ButtonEdit, ButtonEnabled, ButtonDisabled)*/
public abstract class AbstractAjaxFilterColumnPanel extends Panel{
	private final static long serialVersionUID=1L;
	/** �����, ������� ����������� ������ ������ � ������ ��������� �������  */
	private String cssClassEnabled;
	/** �����, ������� ����������� ������ ������ � ������ ������������ ������� */
	private String cssClassDisabled;
	/** ��������� ����  */
	private ModalWindow modalWindow;
	private Model<String> modelCaption=new Model<String>();
	/** �������� �� ������ ������ ��������, �� ���� ��� ������ ������� ���������� */
	private boolean isFilterEnabled=false;
	/** ������������ ������� */
	private String filterCaption;
	/** ������������ ���� ��� ������� � ������� */
	private String sqlFieldName;
	 
	/** ���������� ��� ������� ������� ���������  
	 * @param filterText - ����� ��� ����������� � ���� ������� 
	 */
	public void setFilterCaption(AjaxRequestTarget target, String filterText){
		target.addComponent(this);
		this.modelCaption.setObject(filterText);
	}
	
	/** ������ ��� �������, ������� ���������� ��������� � ������ ��� ������� ���������
	 * @param filterCaption - ������������ �������
	 * @param sqlFieldName - ��� ����, ������� ����� ����������� � ������� 
	 * @param modelCaption - �������������� ��������� ��� �������
	 * @param ajaxIndicator - ��������� �������� 
	 * @param cssClassEnabled - �����, ������� �������� ������ � ������ ��������� ������� 
	 * @param cssClassDisabled - �����, ������� �������� ������ � ������ ������ ������� 
	 */
	public AbstractAjaxFilterColumnPanel(String filterCaption,
			 							 String sqlFieldName, 
										 final String ajaxIndicator,
								 		 String cssClassEnabled,
								 		 String cssClassDisabled){
		super(AjaxHeadersToolbarFilter.idColumn);
		this.filterCaption=filterCaption;
		this.sqlFieldName=sqlFieldName;
		this.setOutputMarkupId(true);
		this.cssClassEnabled=cssClassEnabled;
		this.cssClassDisabled=cssClassDisabled;
		this.add(new SimpleAttributeModifier("class", this.cssClassDisabled));
		this.add(new Label("information", modelCaption));
		
		IndicatingAjaxLink<?> linkEdit=new IndicatingAjaxLink<Object>("link_edit"){
			private final static long serialVersionUID=1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				onButtonEdit(target);
			}
			public String getAjaxIndicatorMarkupId() {
				return ajaxIndicator;
			}
		};
		this.add(linkEdit);
		linkEdit.add(new Label("caption_edit", this.getString("caption_edit")));
		
		IndicatingAjaxLink<?> linkEnable=new IndicatingAjaxLink<Object>("link_enable"){
			private final static long serialVersionUID=1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				onButtonEnable(target);
			}
			 @Override
			public String getAjaxIndicatorMarkupId() {
				return ajaxIndicator;
			}
		};
		this.add(linkEnable);
		linkEnable.add(new Label("caption_enable", this.getString("caption_enable")));
		
		IndicatingAjaxLink<?> linkDisable=new IndicatingAjaxLink<Object>("link_disable"){
			private final static long serialVersionUID=1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				onButtonDisable(target);
			}
			public String getAjaxIndicatorMarkupId() {
				return ajaxIndicator;
			}
		};
		this.add(linkDisable);
		linkDisable.add(new Label("caption_disable", this.getString("caption_disable")));
	
		this.modalWindow=new ModalWindow("modal_window");
		this.add(modalWindow);
	}

	/** ������� �� ������� ������� Edit ��� ������� ������� */
	private void onButtonEdit(AjaxRequestTarget target){
		reactionOnButtonEdit(target);
	}
	/** ������� �� ������ "�������������" ������  (����������� ���������� ���� )
	 * @param target - Ajax ���������, ������� "������" � �������  
	 * */
	protected abstract void reactionOnButtonEdit(AjaxRequestTarget target);
	
	private void onButtonEnable(AjaxRequestTarget target){
		this.isFilterEnabled=true;
		this.add(new SimpleAttributeModifier("class", 
											 this.cssClassEnabled));
		target.addComponent(this);
		reactionOnButtonEnable(target);
	}
	/** ���������� � ������������� ��������� ������� <br> ( ����� �������� ������ ����������)*/
	protected abstract void reactionOnButtonEnable(AjaxRequestTarget target);
	
	private void onButtonDisable(AjaxRequestTarget target){
		this.isFilterEnabled=false;
		this.add(new SimpleAttributeModifier("class", 
											 this.cssClassDisabled));
		target.addComponent(this);
		reactionOnButtonDisable(target);
		
	}
	/** ���������� � ������������� ��������� ������� <br> (����� �������� ������ ���������� )*/
	protected abstract void reactionOnButtonDisable(AjaxRequestTarget target);
	
	/** �������� ��������� ����  */
	protected ModalWindow getModalWindow(){
		this.modalWindow.setTitle(this.getFilterCaption());
		return this.modalWindow;
	}
	
	/** ����� �� ����������� ������:
	 * <ul>
	 * 	<li><b>true</b> ��������� � ������������� ������� </li>
	 * 	<li><b>false</b> �� ��������� � ������������� ������� </li>
	 * </ul>
	 * */
	public boolean isFilterEnabled(){
		return this.isFilterEnabled;
	}
	
	/** �������� ������ � SQL �������� �� ��������� ����� ���� 
	 * @param fieldName - 
	 * @return 
	 * <ul>
	 * 	<li><b>null</b> - ��� ������� ���������� </li>
	 * 	<li><b>SQL �����</b> - SQL ����� ��� ������� � ���� WHERE (��� �������� AND � � �������� �������� ������ � �����)</li>
	 * </ul> 
	 * */
	public abstract String getSqlStringForWhere(String fieldName);

	/** �������� ������ � SQL �������� �� ��������� ����� ���� 
	 * @return 
	 * <ul>
	 * 	<li><b>null</b> - ��� ������� ���������� </li>
	 * 	<li><b>SQL �����</b> - SQL ����� ��� ������� � ���� WHERE (��� �������� AND � � �������� �������� ������ � �����)</li>
	 * </ul> 
	 */
	public String getSqlStringForWhere(){
		return this.getSqlStringForWhere(this.getSqlFieldName());
	}
	
	/** �������� ����������� ������������ ������� ��� ������������ */
	public String getFilterCaption(){
		return this.filterCaption;
	}
	
	/** �������� �������� ������� ��� ������������ <br> <small>(����������� ������������ � �������� ���������� � ��������� ������� )</small> */
	public abstract String getFilterCaptionValue();
	
	/** �������� ����, ������� ����� ����������� � ������� SQL, � ����� WHERE */
	protected String getSqlFieldName(){
		return this.sqlFieldName;
	}
}