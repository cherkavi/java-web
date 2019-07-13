package gui.find_commodity.panels;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import gui.find_commodity.Filter;
import gui.find_commodity.Notify;

public class PanelFilter extends Panel{
	@SuppressWarnings("unused")
	private static void out_debug(String information){
		System.out.print("PanelFilter");
		System.out.print(" DEBUG ");
		System.out.println(information);
	}
	
	private static final long serialVersionUID=1L;
	private Notify field_notify_submit;
	
	private Label label_commodity_name;
	private TextField textfield_commodity_name;
	private Label label_class_id;
	private DropDownChoice choice_class_id;
	private Label label_price_min;
	private TextField textfield_price_min;
	private Label label_price_max;
	private TextField textfield_price_max;
	private Filter field_filter;
	
	public PanelFilter(String id, Notify notify_submit){
		super(id);
		field_notify_submit=notify_submit;
		initComponents();
	}
	
	/** */
	public Filter getFilter(){
		return this.field_filter;
	}
	/** первоначальная инициализация компонентов*/
	@SuppressWarnings("unchecked")
	private void initComponents(){
		this.field_filter=new Filter();
		Form form_main=new Form("form_main",new Model()){
			private final static long serialVersionUID=1L;
			@Override
			public void onSubmit(){
				onFormSubmit();
			}
			@Override
			public void onError(){
				/*
				if(!PanelFilter.this.textfield_price_min.isValid()){
					out_debug("textfield_price_min: Error:");
				}
				if(!PanelFilter.this.textfield_price_max.isValid()){
					out_debug("textfield_price_max: Error:");
				}
				*/
				onFormError();
			}
		};
		
		label_commodity_name=new Label("label_commodity_name",new Model(getCaptionCommodityName()));
		textfield_commodity_name=new TextField("textfield_commodity_name",new PropertyModel(field_filter,"commodity_name"));
		
		label_class_id=new Label("label_class_id",new Model(getCaptionClassId()));
		List list_of_class=database.Utility.getListOfClass();
		
		field_filter.setClass_name((String)(list_of_class.get(0)));
		choice_class_id=new DropDownChoice("choice_class_id",new PropertyModel(field_filter,"class_name"),list_of_class);
		
		label_price_min=new Label("label_price_min",new Model(getCaptionPriceMin()));
		textfield_price_min=new TextField("textfield_price_min",new PropertyModel(field_filter,"price_min"));
		
		label_price_max=new Label("label_price_max",new Model(getCaptionPriceMax()));
		textfield_price_max=new TextField("textfield_price_max",new PropertyModel(field_filter,"price_max"));
		
		Button button_filter=new Button("button_filter",new Model());
		
		form_main.add(label_commodity_name);
		form_main.add(textfield_commodity_name);
		
		form_main.add(label_class_id);
		form_main.add(choice_class_id);
		
		form_main.add(label_price_min);
		form_main.add(textfield_price_min);
		
		form_main.add(label_price_max);
		form_main.add(textfield_price_max);
		form_main.add(button_filter);
		this.add(form_main);
	}
	
	/** reaction on striking button Submit (filter)*/
	private void onFormSubmit(){
		field_notify_submit.notifySubmit(this);
	}
	
	/** reaction on striking button Error (ошибка валидации формы)*/
	private void onFormError(){
		field_notify_submit.notifyError(this);
	}
	
	/** caption for commodity_name */
	private String getCaptionCommodityName(){
		return "Наименование";
	}
	/** caption for class_id */
	private String getCaptionClassId(){
		return "Класс товара";
	}
	/** caption for price_min */
	private String getCaptionPriceMin(){
		return "Мин. цена";
	}
	/** caption for price_max */
	private String getCaptionPriceMax(){
		return "Макс. цена";
	}
	
}


















