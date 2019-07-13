package wicket_utility.ajax.table_filter.toolbar.filter.column;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.table_filter.toolbar.filter.AjaxHeadersToolbarFilter;

/** панель фильтрации для колонки  - базовый функционал (Caption, ButtonEdit, ButtonEnabled, ButtonDisabled)*/
public abstract class AbstractAjaxFilterColumnPanel extends Panel{
	private final static long serialVersionUID=1L;
	/** класс, который назначается данной панели в случае активации фильтра  */
	private String cssClassEnabled;
	/** класс, который назначается данной панели в случае дезактивации фильтра */
	private String cssClassDisabled;
	/** модальное окно  */
	private ModalWindow modalWindow;
	private Model<String> modelCaption=new Model<String>();
	/** является ли данный фильтр активным, то есть при отборе требует фильтрации */
	private boolean isFilterEnabled=false;
	/** наименование фильтра */
	private String filterCaption;
	/** наименование поля для участия в запросе */
	private String sqlFieldName;
	 
	/** установить для данного фильтра заголовок  
	 * @param filterText - текст для отображения в поле фильтра 
	 */
	public void setFilterCaption(AjaxRequestTarget target, String filterText){
		target.addComponent(this);
		this.modelCaption.setObject(filterText);
	}
	
	/** фильтр для колонки, которая отображает заголовок и фильтр для данного заголовка
	 * @param filterCaption - наименование фильтра
	 * @param sqlFieldName - имя поля, которое будет участвовать в запросе 
	 * @param modelCaption - первоначальный заголовок для фильтра
	 * @param ajaxIndicator - индикатор ожидания 
	 * @param cssClassEnabled - класс, который получает панель в случае установки фильтра 
	 * @param cssClassDisabled - класс, который получает панель в случае снятия фильтра 
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

	/** реакция на нажатие клавиши Edit для данного фильтра */
	private void onButtonEdit(AjaxRequestTarget target){
		reactionOnButtonEdit(target);
	}
	/** нажатие на кнопку "редактировать" фильтр  (отображение модального окна )
	 * @param target - Ajax транспорт, который "уходит" к клиенту  
	 * */
	protected abstract void reactionOnButtonEdit(AjaxRequestTarget target);
	
	private void onButtonEnable(AjaxRequestTarget target){
		this.isFilterEnabled=true;
		this.add(new SimpleAttributeModifier("class", 
											 this.cssClassEnabled));
		target.addComponent(this);
		reactionOnButtonEnable(target);
	}
	/** оповещение о необходимости активации фильтра <br> ( можно оставить пустую реализацию)*/
	protected abstract void reactionOnButtonEnable(AjaxRequestTarget target);
	
	private void onButtonDisable(AjaxRequestTarget target){
		this.isFilterEnabled=false;
		this.add(new SimpleAttributeModifier("class", 
											 this.cssClassDisabled));
		target.addComponent(this);
		reactionOnButtonDisable(target);
		
	}
	/** оповещение о необходимости активации фильтра <br> (можно оставить пустую реализацию )*/
	protected abstract void reactionOnButtonDisable(AjaxRequestTarget target);
	
	/** получить модальное окно  */
	protected ModalWindow getModalWindow(){
		this.modalWindow.setTitle(this.getFilterCaption());
		return this.modalWindow;
	}
	
	/** Нужно ли фильтровать данные:
	 * <ul>
	 * 	<li><b>true</b> нуждается в активировании фильтра </li>
	 * 	<li><b>false</b> не нуждается в активировании фильтра </li>
	 * </ul>
	 * */
	public boolean isFilterEnabled(){
		return this.isFilterEnabled;
	}
	
	/** получить строку с SQL условием на основании имени поля 
	 * @param fieldName - 
	 * @return 
	 * <ul>
	 * 	<li><b>null</b> - нет условия фильтрации </li>
	 * 	<li><b>SQL текст</b> - SQL текст для вставки в блок WHERE (без префикса AND и с символом перевода строки в конце)</li>
	 * </ul> 
	 * */
	public abstract String getSqlStringForWhere(String fieldName);

	/** получить строку с SQL условием на основании имени поля 
	 * @return 
	 * <ul>
	 * 	<li><b>null</b> - нет условия фильтрации </li>
	 * 	<li><b>SQL текст</b> - SQL текст для вставки в блок WHERE (без префикса AND и с символом перевода строки в конце)</li>
	 * </ul> 
	 */
	public String getSqlStringForWhere(){
		return this.getSqlStringForWhere(this.getSqlFieldName());
	}
	
	/** получить отображение наименования фильтра для пользователя */
	public String getFilterCaption(){
		return this.filterCaption;
	}
	
	/** получить описание фильтра для пользователя <br> <small>(отображение пользователю в качестве информации о состоянии фильтра )</small> */
	public abstract String getFilterCaptionValue();
	
	/** получить поле, которое будет участвовать в запросе SQL, в блоке WHERE */
	protected String getSqlFieldName(){
		return this.sqlFieldName;
	}
}