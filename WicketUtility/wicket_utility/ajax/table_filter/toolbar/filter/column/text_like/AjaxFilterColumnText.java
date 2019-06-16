package wicket_utility.ajax.table_filter.toolbar.filter.column.text_like;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.IAjaxAction;
import wicket_utility.ajax.table_filter.toolbar.filter.column.AbstractAjaxFilterColumnPanel;
import wicket_utility.ajax.table_filter.toolbar.filter.column.text_like.modal_panel.TextLikePanel;

/** фильтр текстовой строки  */
public class AjaxFilterColumnText extends AbstractAjaxFilterColumnPanel implements IAjaxAction{
	private final static long serialVersionUID=1L;
	private Model<Boolean> modelLeftVariant=new Model<Boolean>(false);
	private Model<String> modelValue=new Model<String>(null);
	private Model<Boolean> modelRightVariant=new Model<Boolean>(false);
	
	/** фильтр текстовой строки  
	 * @param fieldName - наименование фильтра
	 * @param sqlFieldName - имя поля, которое будет участвовать в запросе 
	 * @param ajaxIndicator - индикатор фильтра 
	 * @param cssClassEnabled - класс активации фильтра
	 * @param cssClassDisabled - класс деактивации фильтра 
	 */
	public AjaxFilterColumnText(String fieldName, String sqlFieldName, String ajaxIndicator, String cssClassEnabled, String cssClassDisabled){
		super(fieldName, sqlFieldName, ajaxIndicator, cssClassEnabled, cssClassDisabled);
	}

	@Override
	protected void reactionOnButtonDisable(AjaxRequestTarget target) {}

	@Override
	protected void reactionOnButtonEnable(AjaxRequestTarget target) {}

	@Override
	protected void reactionOnButtonEdit(AjaxRequestTarget target) {
		TextLikePanel panel=new TextLikePanel(this.getModalWindow().getContentId(),this);
		panel.setModelValue(this.modelLeftVariant.getObject(), this.modelValue.getObject(), this.modelRightVariant.getObject());
		// отобразить модальное окно
		this.getModalWindow().setContent(panel);
			// панель для редактирования
		this.getModalWindow().setInitialHeight(120);
		this.getModalWindow().setInitialWidth(300);
		this.getModalWindow().show(target);
	}

	@Override
	public int action(AjaxRequestTarget target, 
					  String name,
					  Object... parameters) {
		if(name.equals(IAjaxAction.action_modal_ok)){
			this.modelLeftVariant.setObject((Boolean)parameters[0]);
			this.modelValue.setObject((String)parameters[1]);
			this.modelRightVariant.setObject((Boolean)parameters[2]);
			this.setFilterCaption(target,
								  ((this.modelLeftVariant.getObject()==true)?"%":"")+
								  this.modelValue.getObject()+
								  ((this.modelRightVariant.getObject()==true)?"%":"")
								  );
			this.getModalWindow().close(target);
			return IAjaxAction.result_ok;
		}else if(name.equals(IAjaxAction.action_modal_cancel)){
			this.getModalWindow().close(target);
			return IAjaxAction.result_ok;
		}else {
			assert false: "AjaxFilterColumnText#action not found: ";
			return IAjaxAction.result_error;
		}
	}
	
	/** нужно ли добавить в запрос левый знак процента - любое кол-во символов в левой части  */
	public Boolean getLeftVariant(){
		return this.modelLeftVariant.getObject();
	}
	
	public Boolean getRightVariant(){
		return this.modelRightVariant.getObject();
	}

	@Override
	public String getFilterCaptionValue() {
		if(this.modelValue.getObject()!=null){
			return ((this.modelLeftVariant.getObject()==true)?"%":"")+
			this.modelValue.getObject()+
			((this.modelRightVariant.getObject()==true)?"%":"");
		}else{
			return "";
		}
	}

	@Override
	public String getSqlStringForWhere(String fieldName) {
		if((this.modelValue.getObject()!=null)&&(!this.modelValue.getObject().trim().equals(""))){
			return  fieldName+" like '"+
					((this.modelLeftVariant.getObject()==true)?"%":"")+
					this.modelValue.getObject().replaceAll("'", "''")+
					((this.modelRightVariant.getObject()==true)?"%":"")+
					"'";
		}
		return null;
	}
	
	
}
