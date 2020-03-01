package wicket_utility.ajax.table_filter.toolbar.filter.column.select_distinct;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.IAjaxActionExecutor;
import wicket_utility.ajax.table_filter.toolbar.filter.column.AbstractAjaxFilterColumnPanel;
import wicket_utility.ajax.table_filter.toolbar.filter.column.select_distinct.modal_panel.DistinctPanel;

/** панель с интерфейсом фильтра с заданными параметрами,
 * отображает DropDownChoice и оповещает Ajax запросами о выборе очередного значения */
public class AjaxFilterColumnDistinct extends AbstractAjaxFilterColumnPanel implements IAjaxActionExecutor{
	private final static long serialVersionUID=1L;
	/** источник данных для передачи в фильтр  */
	private ISelectDataSource dataSource;
	/** модель текущего выбранного элемента  */
	private Model<String> modelCurrentChoice=new Model<String>(null);

	/**
	 * @param dataSource - источник данных для выбора
	 * @param filterName - наименование фильтра
	 * @param sqlFieldName - имя поля, которое будет участвовать в запросе 
	 * @param ajaxIndicator - индикатор ожидания для Ajax
	 * @param cssClassEnabled - класс CSS, который присвоится панели в случае установки фильтра  
	 * @param cssClassDisabled - класс CSS, который присвоится панели в случае снятия фильтра  
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
		// поднять модальное окно и передать в данное окно модель данного компонента
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
			// пользователь выбрал вариант фильтра
			this.modelCurrentChoice.setObject((String)parameters[0]);
			this.getModalWindow().close(target);
			this.setFilterCaption(target, this.modelCurrentChoice.getObject());
			return IAjaxActionExecutor.result_ok;
		}else if(name.equals(IAjaxActionExecutor.action_modal_cancel)){
			// пользователь отменил выбор 
			this.getModalWindow().close(target);
			return IAjaxActionExecutor.result_ok;
		}else{
			assert false: "AjaxFilterColumnDistinct непонятное меню выбрано пользователем ";
			return IAjaxActionExecutor.result_error;
		}
	}
	
	/** получить текущее значение фильтра */
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
