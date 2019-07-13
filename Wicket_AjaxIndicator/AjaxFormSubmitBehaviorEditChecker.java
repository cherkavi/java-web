package wicket_utility.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;

/** объект, который задает отсылку Ajax формы на сервер, c методом анализа и возможностью установки видимых изображений (Ok, Error) */
public abstract class AjaxFormSubmitBehaviorEditChecker extends AjaxFormSubmitBehavior{
	private final static long serialVersionUID=1L;
	private Image imageOk;
	private Image imageError;
	private String indicatorHtmlId;
	
	/** объект, который задает отсылку Ajax формы на сервер, c методом анализа и возможностью установки видимых изображений (Ok, Error)   
	 * @param form - форма, которая должна посылаться на сервер, которая содержит поле для проверки 
	 * @param event - событие, по которому данная форма будет отправлена ( например "onkeyup" )
	 * @param imageOk - валидность ввода 
	 * @param imageError - ошибочный ввод 
	 * @param indicatorHtmlId - HTML id изображения, которое будет отображать индикатор ожидания
	 */
	public AjaxFormSubmitBehaviorEditChecker(Form<?> form, 
											 String event,
											 Image imageOk,
											 Image imageError,
											 String indicatorHtmlId){
		super(form, event);
		this.imageOk=imageOk;
		this.imageOk.setOutputMarkupId(true);
		this.imageOk.setOutputMarkupPlaceholderTag(true);
		
		this.imageError=imageError;
		this.imageError.setOutputMarkupId(true);
		this.imageError.setOutputMarkupPlaceholderTag(true);
		
		this.indicatorHtmlId=indicatorHtmlId;
	}

	@Override
	protected String findIndicatorId() {
		return this.indicatorHtmlId;
	}
	
	@Override
	protected void onError(AjaxRequestTarget target) {
		System.err.println("AjaxFormSubmitBehaviorEditChecker Error");
	}

	private void setImageOkVisible(boolean visible){
		this.imageOk.setVisible(visible);
	}
	private void setImageErrorVisible(boolean visible){
		this.imageError.setVisible(visible);
	}
	
	@Override
	protected void onSubmit(AjaxRequestTarget target) {
		if(showImageOk()){
			this.setImageOkVisible(true);
			this.setImageErrorVisible(false);
		}else{
			this.setImageOkVisible(false);
			this.setImageErrorVisible(true);
		}
		target.addComponent(this.imageOk);
		target.addComponent(this.imageError);
	}
	
	/** отображать положительное изображение
	 * @return 
	 * <li> <b>true</b> отображать положительное изображение</li>
	 * <li> <b>false</b> отображать ошибочный ввод </li>
	 */
	protected abstract boolean showImageOk();
}
