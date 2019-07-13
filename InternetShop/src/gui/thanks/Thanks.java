package gui.thanks;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import utility.ShopSession;

/** страница либо с отображением благодарности для клиента, либо же страница с отображением
 * произошедшей ошибкой сохранения заказа 
 */
public class Thanks extends WebPage{
	
	/** страница либо с отображением благодарности для клиента 
	 */
	public Thanks(){
		initComponents(null);
	}
	/** создать страницу с отображением ошибки сохранения данных 
	 * @param error_message - сообщение, которое отображает страницу с информацией об ошибках
	 * */
	public Thanks(String error_message){
		initComponents(error_message);
	}

	/** первоначальная инициализация компонентов 
	 * @param error_messasge - информация, которая должна быть отображена на панели ошибочного вывода данных 
	 * */
	private void initComponents(String error_message){
		Panel panel_information=null;
		if(error_message==null){
			this.add(new Label("label_title",getReadOnlyModel(error_message)));
			// show valid message
			panel_information=new PanelOk("panel_information","Thanks");
		}else{
			// show error message
			panel_information=new PanelError("panel_information","Thanks");
		}
		this.add(panel_information);
		Form form_main=new Form("form_main");
		form_main.add(new Button("button_ok"){
			private static final long serialVersionUID=1L;
			@Override
			public void onSubmit(){
				setResponsePage( ((ShopSession)getSession()).popWebPage() );
			}
		});
		this.add(form_main);
	}
	
	/** получить модель только для чтения */
	private IModel getReadOnlyModel(final Object value){
		return new AbstractReadOnlyModel(){
			private static final long serialVersionUID=1L;
			@Override
			public Object getObject() {
				return value;
			}
		};
	}
}
