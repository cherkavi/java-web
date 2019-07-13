package wicket_utility.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.image.Image;

/** ������, ������� ������ ������� Ajax ����� �� ������, c ������� ������� � ������������ ��������� ������� ����������� (Ok, Error) */
public abstract class AjaxFormSubmitBehaviorEditChecker extends  AjaxFormComponentUpdatingBehavior{ // AjaxFormSubmitBehavior{
	private final static long serialVersionUID=1L;
	private Image imageOk;
	private Image imageError;
	private String indicatorHtmlId;
	
	/** ������, ������� ������ ������� Ajax ����� �� ������, c ������� ������� � ������������ ��������� ������� ����������� (Ok, Error)   
	 * @param form - �����, ������� ������ ���������� �� ������, ������� �������� ���� ��� �������� 
	 * @param event - �������, �� �������� ������ ����� ����� ���������� ( �������� "onkeyup" )
	 * @param imageOk - ���������� ����� 
	 * @param imageError - ��������� ���� 
	 * @param indicatorHtmlId - HTML id �����������, ������� ����� ���������� ��������� ��������
	 */
	public AjaxFormSubmitBehaviorEditChecker(String event,
											 Image imageOk,
											 Image imageError,
											 String indicatorHtmlId){
		super(event);
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
	
	/*@Override
	protected void onError(AjaxRequestTarget target) {
		System.err.println("AjaxFormSubmitBehaviorEditChecker Error");
		process(target);
	}
	@Override
	protected void onSubmit(AjaxRequestTarget target) {
		process(target);
	}*/
	
	protected void onUpdate(AjaxRequestTarget target) {
		process(target);
	}

	private void setImageOkVisible(boolean visible){
		this.imageOk.setVisible(visible);
	}
	private void setImageErrorVisible(boolean visible){
		this.imageError.setVisible(visible);
	}
	

	private void process(AjaxRequestTarget target){
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
	
	/** ���������� ������������� �����������
	 * @return 
	 * <li> <b>true</b> ���������� ������������� �����������</li>
	 * <li> <b>false</b> ���������� ��������� ���� </li>
	 */
	protected abstract boolean showImageOk();
}
