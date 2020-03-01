package wicket_utility.ajax.table_filter.toolbar.filter.column.select_distinct;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.IAjaxActionExecutor;
import wicket_utility.ajax.table_filter.toolbar.filter.column.AbstractAjaxFilterColumnPanel;
import wicket_utility.ajax.table_filter.toolbar.filter.column.select_distinct.modal_panel.DistinctPanel;

/** ������ � ����������� ������� � ��������� �����������,
 * ���������� DropDownChoice � ��������� Ajax ��������� � ������ ���������� �������� */
public class AjaxFilterColumnDistinct extends AbstractAjaxFilterColumnPanel implements IAjaxActionExecutor{
	private final static long serialVersionUID=1L;
	/** �������� ������ ��� �������� � ������  */
	private ISelectDataSource dataSource;
	/** ������ �������� ���������� ��������  */
	private Model<String> modelCurrentChoice=new Model<String>(null);

	/**
	 * @param dataSource - �������� ������ ��� ������
	 * @param filterName - ������������ �������
	 * @param sqlFieldName - ��� ����, ������� ����� ����������� � ������� 
	 * @param ajaxIndicator - ��������� �������� ��� Ajax
	 * @param cssClassEnabled - ����� CSS, ������� ���������� ������ � ������ ��������� �������  
	 * @param cssClassDisabled - ����� CSS, ������� ���������� ������ � ������ ������ �������  
	 */
	public AjaxFilterColumnDistinct(ISelectDataSource dataSource,
									String filterName,
									String sqlFieldName,
									String ajaxIndicator, 
									String cssClassEnabled,
									String cssClassDisabled) {
		super(filterName, 
			  sqlFieldName,
			  ajaxIndicator, 
			  cssClassEnabled, 
			  cssClassDisabled);
		this.dataSource=dataSource;
	}

	@Override
	protected void reactionOnButtonEdit(AjaxRequestTarget target) {
		// ������� ��������� ���� � �������� � ������ ���� ������ ������� ����������
		DistinctPanel distinctPanel=new DistinctPanel(this.getModalWindow().getContentId(), 
													  this);
		ArrayList<String> listOfValues=this.dataSource.getValues();
		int selectedIndex=listOfValues.indexOf(this.modelCurrentChoice.getObject());
		if(selectedIndex<0){
			selectedIndex=0;
		}
		distinctPanel.setModelValue(listOfValues, 
									selectedIndex);
		this.getModalWindow().setContent(distinctPanel);
		this.getModalWindow().setInitialHeight(70);
		this.getModalWindow().setInitialWidth(250);
		this.getModalWindow().show(target);
	}

	@Override
	protected void reactionOnButtonEnable(AjaxRequestTarget target) {}
	
	@Override
	protected void reactionOnButtonDisable(AjaxRequestTarget target) {}

	@Override
	public int action(AjaxRequestTarget target, 
					   String name,
					   Object... parameters) {
		if(name.equals(IAjaxActionExecutor.action_modal_ok)){
			// ������������ ������ ������� �������
			this.modelCurrentChoice.setObject((String)parameters[0]);
			this.getModalWindow().close(target);
			this.setFilterCaption(target, this.modelCurrentChoice.getObject());
			return IAjaxActionExecutor.result_ok;
		}else if(name.equals(IAjaxActionExecutor.action_modal_cancel)){
			// ������������ ������� ����� 
			this.getModalWindow().close(target);
			return IAjaxActionExecutor.result_ok;
		}else{
			assert false: "AjaxFilterColumnDistinct ���������� ���� ������� ������������� ";
			return IAjaxActionExecutor.result_error;
		}
	}
	
	/** �������� ������� �������� ������� */
	public String getFilterValue(){
		return this.modelCurrentChoice.getObject();
	}

	@Override
	public String getFilterCaptionValue() {
		if(this.modelCurrentChoice.getObject()!=null){
			return this.modelCurrentChoice.getObject();
		}else{
			return "";
		}
	}

	@Override
	public String getSqlStringForWhere(String fieldName) {
		if((this.modelCurrentChoice.getObject()!=null)&&(!this.modelCurrentChoice.getObject().trim().equals(""))){
			return fieldName+"='"+this.modelCurrentChoice.getObject().replaceAll("'", "''")+"'";
		}else{
			return null;
		}
	}
}
