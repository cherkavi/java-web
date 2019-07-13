package wicket_utility.ajax.table_filter.toolbar.filter.column.date_between;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.IAjaxAction;
import wicket_utility.ajax.table_filter.toolbar.filter.column.AbstractAjaxFilterColumnPanel;
import wicket_utility.ajax.table_filter.toolbar.filter.column.date_between.modal_panel.DateBetweenPanel;

/** панель фильтрации между двумя датами */
public class AjaxFilterColumnDateBetween extends AbstractAjaxFilterColumnPanel implements IAjaxAction{
	private final static long serialVersionUID=1L;
	private Model<Date> modelDateBegin=new Model<Date>(null);
	private Model<Date> modelDateEnd=new Model<Date>(null);

	/** панель фильтрации между двумя датами
	 * @param filterName - заголовок фильтра
	 * @param sqlFieldName - имя поля, которое будет участвовать в запросе 
	 * @param ajaxIndicator - индикатор Ajax 
	 * @param cssClassEnabled - стиль активации фильтра
	 * @param cssClassDisabled - стиль деактивации фильтра 
	 */
	public AjaxFilterColumnDateBetween(String filterName,
									   String sqlFieldName,
									   String ajaxIndicator, 
									   String cssClassEnabled, 
									   String cssClassDisabled){
		super(filterName, sqlFieldName, ajaxIndicator, cssClassEnabled, cssClassDisabled);
	}
	
	
	@Override
	protected void reactionOnButtonDisable(AjaxRequestTarget target) {}

	@Override
	protected void reactionOnButtonEdit(AjaxRequestTarget target) {
		// отобразить модальное окно
		DateBetweenPanel content=new DateBetweenPanel(this.getModalWindow().getContentId(),this);
		content.setModelValue(this.modelDateBegin.getObject(), this.modelDateEnd.getObject());
		this.getModalWindow().setContent(content);
		this.getModalWindow().setInitialHeight(90);
		this.getModalWindow().setInitialWidth(300);
		
		this.getModalWindow().show(target);
	}

	@Override
	protected void reactionOnButtonEnable(AjaxRequestTarget target) {}

	private SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
	
	private String getCaption(Date dateBegin, Date dateEnd){
		String returnValue="";
		try{
			returnValue=sdf.format(dateBegin);
		}catch(Exception ex){
			System.err.println("AjaxFilterColumnDateBetween  Exception:"+ex.getMessage());
		}
		try{
			returnValue+="..."+sdf.format(dateEnd);
		}catch(Exception ex){
			System.err.println("AjaxFilterColumnDateBetween  Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	@Override
	public int action(AjaxRequestTarget target, 
					   String name,
					   Object... parameters) {
		if(name.equals(IAjaxAction.action_modal_ok)){
			// пользователь применил изменения 
			this.modelDateBegin.setObject((Date)parameters[0]);
			this.modelDateEnd.setObject((Date)parameters[1]);
			this.setFilterCaption(target, this.getCaption(this.modelDateBegin.getObject(), this.modelDateEnd.getObject()));
			this.getModalWindow().close(target);
			return IAjaxAction.result_ok;
		}if(name.equals(IAjaxAction.action_modal_cancel)){
			// пользователь отказался от изменений 
			this.getModalWindow().close(target);
			return IAjaxAction.result_ok;
		}else{
			assert false: "AjaxFilterColumnDate: Action from modal window does not recognized ";
			return IAjaxAction.result_error;
		}
	}
	
	/** фильтр: дата начала */
	public Date getDateBegin(){
		return this.modelDateBegin.getObject();
	}

	/** фильтр: дата окончания */
	public Date getDateEnd(){
		return this.modelDateEnd.getObject();
	}

	private Date getNextDay(Date date){
		if(date!=null){
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			return calendar.getTime();
		}else{
			return null;
		}
	}
	
	@Override
	public String getFilterCaptionValue() {
		if((this.modelDateBegin.getObject()!=null)&&(this.modelDateEnd.getObject()!=null)){
			return "[  X  ] >='"+formatSql.format(this.modelDateBegin.getObject())+"' and [  X  ]<'"+formatSql.format(getNextDay(this.modelDateEnd.getObject()))+"'"; 
		}else{
			if(this.modelDateBegin.getObject()!=null){
				// dateBegin is null
				return ">="+formatSql.format(this.modelDateBegin.getObject());
			}else if(this.modelDateEnd.getObject()!=null){
				// dateEnd is null
				return "<"+formatSql.format(getNextDay(this.modelDateEnd.getObject()));
			}else{
				return "";
			}
		}
	}


	private SimpleDateFormat formatSql=new SimpleDateFormat("dd.MM.yyyy");
	@Override
	public String getSqlStringForWhere(String fieldName) {
		if(fieldName==null){
			fieldName=this.getSqlFieldName();
		}
		if((this.modelDateBegin.getObject()!=null)&&(this.modelDateEnd.getObject()!=null)){
			return fieldName+">=TO_DATE('"+formatSql.format(this.modelDateBegin.getObject())+"','DD.MM.RRRR') and "+fieldName+"<TO_DATE('"+formatSql.format(getNextDay(this.modelDateEnd.getObject()))+"','DD.MM.RRRR') \n"; 
		}else{
			if(this.modelDateBegin.getObject()!=null){
				// dateBegin is null
				return fieldName+">=TO_DATE('"+formatSql.format(this.modelDateBegin.getObject()+"','DD.MM.RRRR')");
			}else if(this.modelDateEnd.getObject()!=null){
				// dateEnd is null
				return fieldName+"<TO_DATE('"+formatSql.format(getNextDay(this.modelDateEnd.getObject())+"','DD.MM.RRRR')");
			}else{
				return null;
			}
		}
	}
}
