package wicket_utility.ajax.table_filter.toolbar.filter.column.number_value;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import wicket_utility.ajax.IAjaxAction;
import wicket_utility.ajax.table_filter.toolbar.filter.column.AbstractAjaxFilterColumnPanel;
import wicket_utility.ajax.table_filter.toolbar.filter.column.number_value.modal_panel.NumberPanel;

/** фильтр текстовой строки  */
public class AjaxFilterColumnNumber extends AbstractAjaxFilterColumnPanel implements IAjaxAction{
	private final static long serialVersionUID=1L;

	private Model<String> modelValue=new Model<String>(null);
	private Model<Boolean> modelGt=new Model<Boolean>(true);
	private Model<Boolean> modelGe=new Model<Boolean>(false);
	private Model<Boolean> modelLt=new Model<Boolean>(false);
	private Model<Boolean> modelLe=new Model<Boolean>(false);
	private Model<Boolean> modelEq=new Model<Boolean>(false);
	private Model<Boolean> modelNe=new Model<Boolean>(false);
	
	/** фильтр текстовой строки
	 * @param filterName - наименование фильтра   
	 * @param sqlFieldName - им€ пол€, которое будет участвовать в запросе 
	 * @param ajaxIndicator - индикатор ожидани€ ответа на XmlHttpRequest  
	 * @param cssClassEnabled - класс дл€ Enabled фильтра 
	 * @param cssClassDisabled - класс дл€ Disabled фильтра
	 */
	public AjaxFilterColumnNumber(String filterName,
							      String sqlFieldName, 
								  String ajaxIndicator, 
								  String cssClassEnabled, 
								  String cssClassDisabled){
		super(filterName, sqlFieldName, ajaxIndicator, cssClassEnabled, cssClassDisabled);
	}

	@Override
	protected void reactionOnButtonDisable(AjaxRequestTarget target) {}

	@Override
	protected void reactionOnButtonEnable(AjaxRequestTarget target) {}

	@Override
	protected void reactionOnButtonEdit(AjaxRequestTarget target) {
		// отобразить модальное окно
		NumberPanel panel=new NumberPanel(this.getModalWindow().getContentId(),this);
		panel.setModelValue(this.modelGt.getObject(),
							this.modelGe.getObject(),
							this.modelLt.getObject(),
							this.modelLe.getObject(),
							this.modelEq.getObject(),
							this.modelNe.getObject(),
							this.modelValue.getObject()
							);
		this.getModalWindow().setContent(panel);
			// панель дл€ редактировани€
		this.getModalWindow().setInitialHeight(170);
		this.getModalWindow().setInitialWidth(300);
		this.getModalWindow().show(target);
	}

	private String getMessageLabel(){
		String returnValue="";
		if(this.modelGt.getObject()==true){
			returnValue=">";
		}
		if(this.modelGe.getObject()==true){
			returnValue=">=";
		}
		if(this.modelLt.getObject()==true){
			returnValue="<";
		}
		if(this.modelLe.getObject()==true){
			returnValue="<=";
		}
		if(this.modelEq.getObject()==true){
			returnValue="=";
		}
		if(this.modelNe.getObject()==true){
			returnValue="<>";
		}
		returnValue=returnValue+this.modelValue.getObject();
		return returnValue;
	}
	
	@Override
	public int action(AjaxRequestTarget target, 
					  String name,
					  Object... parameters) {
		if(name.equals(IAjaxAction.action_modal_ok)){
			this.modelGt.setObject((Boolean)parameters[0]);
			this.modelGe.setObject((Boolean)parameters[1]);
			this.modelLt.setObject((Boolean)parameters[2]);
			this.modelLe.setObject((Boolean)parameters[3]);
			this.modelEq.setObject((Boolean)parameters[4]);
			this.modelNe.setObject((Boolean)parameters[5]);
			this.modelValue.setObject((String)parameters[6]);
			this.getModalWindow().close(target);
			this.setFilterCaption(target, this.getMessageLabel());
			return IAjaxAction.result_ok;
		}else if(name.equals(IAjaxAction.action_modal_cancel)){
			this.getModalWindow().close(target);
			return IAjaxAction.result_ok;
		}else {
			assert false: "AjaxFilterColumnText#action not found: ";
			return IAjaxAction.result_error;
		}
	}
	
	/** Ѕольше */
	public Boolean getFilterGt(){
		return this.modelGt.getObject();
	}
	
	/** Ѕольше либо равно  */
	public Boolean getFilterGe(){
		return this.modelGe.getObject();
	}
	
	/** ћеньше */
	public Boolean getFilterLt(){
		return this.modelLt.getObject();
	}
	
	/** ћеньше либо равно  */
	public Boolean getFilterLe(){
		return this.modelLe.getObject();
	}
	
	/** равно */
	public Boolean getFilterEq(){
		return this.modelEq.getObject();
	}

	/** не равно */
	public Boolean getFilterNe(){
		return this.modelNe.getObject();
	}
	
	/** получить значение текстовой переменной */
	public String getFilterValue(){
		return this.modelValue.getObject();
	}

	/** получить знак на основании модели */
	private String getSign(){
		if(this.modelEq.getObject()){
			return "=";
		}else if(this.modelGe.getObject()){
			return ">=";
		}else if(this.modelGt.getObject()){
			return ">";
		}else if(this.modelLe.getObject()){
			return "<=";
		}else if(this.modelLt.getObject()){
			return "<";
		}else if(this.modelNe.getObject()){
			return "<>";
		}else{
			return null;
		}
	}

	@Override
	public String getFilterCaptionValue() {
		Integer value=0;
		try{
			value=Integer.parseInt(this.modelValue.getObject());
		}catch(Exception ex){};
		if(this.getSign()!=null){
			return this.getSign()+value;
		}else{
			return "";
		}
	}

	@Override
	public String getSqlStringForWhere(String fieldName) {
		Integer value=0;
		try{
			value=Integer.parseInt(this.modelValue.getObject());
		}catch(Exception ex){};
		if(this.getSign()!=null){
			return fieldName+this.getSign()+value;
		}else{
			return null;
		}
	}
}
