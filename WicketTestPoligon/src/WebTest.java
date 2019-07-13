import java.io.Serializable;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import java.util.ArrayList;


public class WebTest extends WebPage{
	/** вывод отладочной информации */
	private void out_debug(String information){
		System.out.print("WebTest");
		System.out.print(" DEBUG ");
		System.out.println(information);
	}
	/** вывод ошибочной информации */
	private void out_error(String information){
		System.out.print("WebTest");
		System.out.print(" ERROR ");
		System.out.println(information);
	}
	
	private Model field_label_model=new Model("test application");
	private CheckBoxModel field_checkbox_model_object=new CheckBoxModel();
	private PropertyModel field_checkbox_model=new PropertyModel(field_checkbox_model_object,"checkbox");
	
	public WebTest(){
		// create component's
		Form form=new Form("form_main"){
			private static final long serialVersionUID=1L;
			public void onSubmit(){
				out_debug("onSubmit");
				out_debug("Checkbox model value:"+field_checkbox_model_object.getCheckbox());
			}
		};
		Label label=new Label("label",field_label_model);
		CheckBox checkbox=new CheckBox("checkbox",field_checkbox_model);
		Link link_a=new Link("link_a"){
			private static final long serialVersionUID=1L;
			@Override
			public void onClick() {
				out_debug("link_a");
			}
		};
		Link link_b=new Link("link_b"){
			private static final long serialVersionUID=1L;
			@Override
			public void onClick() {
				out_debug("link_b");
			}
		};
		
		// placing component's
		this.add(label);
		this.add(form);
		form.add(checkbox);
		form.add(link_a);
		form.add(link_b);

		ArrayList<String> list_of_data=new ArrayList<String>();
		list_of_data.add("one");
		list_of_data.add("two");
		list_of_data.add("three");
		
		RadioButtonData data_choice_group=new RadioButtonData();
		// --------------------------
		Form form_choice_group=new Form("form_choice_group",new CompoundPropertyModel(data_choice_group)){
			private final static long serialVersionUID=1L;
			public void onSubmit(){
				out_debug("form_choice_group onSubmit:"+((RadioButtonData)this.getModelObject()).getRadio_choice());
			}
		};
		RadioChoice choice_group=new RadioChoice("radio_choice",list_of_data);
		Button button_radio_choice=new Button("submit_radio_choice");
		form_choice_group.add(choice_group);
		form_choice_group.add(button_radio_choice);
		add(form_choice_group);
			
		// ----------------------
		final RadioButtonData data=new RadioButtonData();
		data.setRadio_group("one");
		Form form_radio_button=new Form("form_radio_button", new CompoundPropertyModel(data)){
			private static final long serialVersionUID=1L;
			public void onSubmit(){
				System.out.println("Form onSubmit():"+this.getModelObjectAsString());
				out_debug("Form onSubmit: Data:"+((RadioButtonData)this.getModelObject()).getData());
				out_debug("Form onSubmit: RadioGroup:"+((RadioButtonData)this.getModelObject()).getRadio_group());
				out_debug("Form onSubmit: RadioChoice:"+((RadioButtonData)this.getModelObject()).getRadio_choice());

				out_debug("Form onSubmit: Data:"+data.getData());
				out_debug("Form onSubmit: RadioGroup:"+data.getRadio_group());
				out_debug("Form onSubmit: RadioChoice:"+data.getRadio_choice());
			}
		};
		
		RadioGroup radio_group=new RadioGroup("radio_group");
		ListView radio_button_list=new ListView("radio_button_list",list_of_data){
			private final static long serialVersionUID=1L;
			@Override
			protected void populateItem(ListItem item) {
				// !!! important - item.getModel();
				item.add(new Radio("radio_button",item.getModel()));
				item.add(new Label("radio_button_label",item.getModelObjectAsString()));
			}
		};
		radio_group.add(radio_button_list);	
		form_radio_button.add(radio_group);
		Button submit_radio_button=new Button("submit_radio_button"){
			private static final long serialVersionUID=1L;
			public void onSubmit(){
				System.out.println("Button onSubmit():"+this.getModelObjectAsString());
			}
		};
		form_radio_button.add(submit_radio_button);
		this.add(form_radio_button);
			
	}
}

class RadioButtonData implements Serializable{
	private final static long serialVersionUID=1L;
	private String data;
	private String radio_group;
	private String radio_choice;
	

	public String getRadio_choice() {
		return radio_choice;
	}

	public void setRadio_choice(String radio_choice) {
		this.radio_choice = radio_choice;
	}

	public RadioButtonData(){
		data="";
		radio_group="";
	}
	
	public String getRadio_group() {
		return radio_group;
	}

	public void setRadio_group(String radio_group) {
		this.radio_group = radio_group;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}

class CheckBoxModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private Boolean field_value;
	
	public CheckBoxModel(){
		this.field_value=new Boolean(true);
	}
	public CheckBoxModel(boolean value){
		this.field_value=new Boolean(value);
	}
	
	public void setCheckbox(Boolean value){
		this.field_value=value;
	}
	public Boolean getCheckbox(){
		return this.field_value;
	}
}

