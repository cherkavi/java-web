package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment.window_set_price;

import java.util.ArrayList;

import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment.ClassIdentifier;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment.CommodityAssortment;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment.CommodityAssortmentQuestion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class WindowSetPrice extends Window{
	private CommodityAssortmentQuestion commodityAssortmentQuestion;
	
	private TextField<String> fieldName;
	private TextField<String> fieldKod;
	private TextField<String> fieldWarranty;
	private ListBox fieldSelectClass=null;
	private TextField<String> fieldPriceBuy;
	private TextField<String> fieldPriceSell;
	
	private ArrayList<ClassIdentifier> fieldListOfClasses;
	
	private CommodityAssortment manager;
	
	public WindowSetPrice(ArrayList<ClassIdentifier> listOfClasses, CommodityAssortmentQuestion commodityAssortment, CommodityAssortment manager){
		this.manager=manager;
		this.fieldListOfClasses=listOfClasses;
		this.commodityAssortmentQuestion=commodityAssortment;
		initComponents(listOfClasses);
		loadAssortmentCommodityToVisualComponents();
		this.setWidth(300);
		this.setHeight(500);
		this.setAutoHeight(true);
	}

	public CommodityAssortmentQuestion getCommodityAssortmentQuestion(){
		return this.commodityAssortmentQuestion;
	}
	
	/** нажатие на кнопку Save */
	private void onButtonSave(){
		if((fieldPriceBuy.validate())&&(fieldPriceSell.validate())){
			this.saveVisualComponentsToAssortmentCommodity();
			this.hide();
			manager.nextProcessPriceSellIteration();
		}else{
			// showing notification message
		}
	}
	
	/** нажатие на кнопку Cancel */
	private void onButtonCancel(){
		this.hide();
	}
	
	/** первоначальная инициализация компонентов */
	private void initComponents(ArrayList<ClassIdentifier> classVariants){
		this.setModal(true);
		
		RowLayout layout=new RowLayout();
		layout.setOrientation(Orientation.VERTICAL);
		this.setLayout(layout);
		
		fieldName=new TextField<String>();
		fieldName.setReadOnly(true);
		fieldName.setAutoWidth(true);
		
		fieldKod=new TextField<String>();
		fieldKod.setReadOnly(true);
		
		
		fieldSelectClass=new ListBox();
		for(int counter=0;counter<classVariants.size();counter++){
			fieldSelectClass.addItem(classVariants.get(counter).getName(), classVariants.get(counter).getKod().toString());
		}
		fieldSelectClass.setSelectedIndex(0);
		
		fieldWarranty=new TextField<String>();
		fieldWarranty.setValidator(new Validator(){
			@Override
			public String validate(Field<?> field, String value) {
				if(value==null){
					return "Enter value";
				}
				if(value.trim().equals("")){
					return "enter value";
				}
				int currentValue=0;
				try{
					currentValue=Integer.parseInt(value);
				}catch(Exception ex){
					return "Enter float value";
				}
				if(currentValue<0){
					return "enter positive value";
				}
				return null;
			}
		});
		
		fieldPriceBuy=new TextField<String>();
		fieldPriceBuy.setReadOnly(true);
		
		fieldPriceSell=new TextField<String>();
		fieldPriceSell.setValidator(new Validator(){
			@Override
			public String validate(Field<?> field, String value) {
				if(value==null){
					return "Enter value";
				}
				if(value.trim().equals("")){
					return "enter value";
				}
				
				float currentValue=0;
				try{
					currentValue=Float.parseFloat(value);
				}catch(Exception ex){
					return "Enter float value";
				}
				
				if(currentValue<0){
					return "enter positive value";
				}
				
				Float pBuy=getFloatFromString(fieldPriceBuy.getValue());
				if((pBuy!=null)&&(pBuy.floatValue()>=currentValue)){
					return "Check value";
				}
				return null;
			}
		});

		Button buttonSave=new Button("Save"){
			protected void onClick(com.extjs.gxt.ui.client.event.ComponentEvent ce) {
				onButtonSave();
			};
		};
		
		Button buttonCancel=new Button("Cancel"){
			protected void onClick(com.extjs.gxt.ui.client.event.ComponentEvent ce) {
				onButtonCancel();
			};
		};
		/*
		this.add(getContainer(getTitlePanel("name",this.fieldName)));
		this.add(getContainer(getTitlePanel("kod",this.fieldKod),
							  getContainer(getTitlePanel("class",this.fieldSelectClass))
							  ) 
				);
		this.add(getContainer(getTitlePanel("price buy",this.fieldPriceBuy),
				 			  getTitlePanel("price sell",this.fieldPriceSell),
				 			  getTitlePanel("warranty",this.fieldWarranty))
				 ); 
		this.add(getContainer(buttonSave, buttonCancel));
		*/
		this.add(this.getTitlePanel("Name",this.fieldName));
		this.add(this.getTitlePanel("Kod",this.fieldKod));
		this.add(this.getTitlePanel("Classes",this.fieldSelectClass));
		this.add(this.getTitlePanel("Price Buy",this.fieldPriceBuy));
		this.add(this.getTitlePanel("Price Sell",this.fieldPriceSell));
		this.add(this.getTitlePanel("Warranty",this.fieldWarranty));
		this.add(this.getContainer(buttonSave, buttonCancel));
	}
	
	/** 
	 * преобразовать String в Float
	 * @param value - строковое значение
	 * @return 
	 * <ul>
	 * 	<li><b>null</b> - ошибка получения значения </li>
	 * 	<li><b>value</b> - float значение </li>
	 * </ul>
	 */
	private Float getFloatFromString(String value){
		try{
			return Float.parseFloat(value.replaceAll(",", "."));
		}catch(Exception ex){
			return null;
		}
	}
	
	/** получить контейнер */
	private LayoutContainer getContainer(Widget ... widgets){
		LayoutContainer container=new LayoutContainer();
		FillLayout layout=new FillLayout();
		layout.setOrientation(Orientation.HORIZONTAL);
		container.setLayout(layout);
		for(int counter=0;counter<widgets.length;counter++){
			container.add(widgets[counter]);
		}
		return container;
	}
	
	/** получить панель с заголовком */
	private VerticalPanel getTitlePanel(String title, Widget widget){
		VerticalPanel returnValue=new VerticalPanel();
		returnValue.setAutoWidth(true);
		returnValue.setBorders(true);
		// returnValue.setTitle(title);
		returnValue.add(new Label(title));
		returnValue.add(widget);
		return returnValue;
	}
	
	private void loadAssortmentCommodityToVisualComponents(){
		this.fieldKod.setValue(Integer.toString(this.commodityAssortmentQuestion.getKod()));
		this.fieldName.setValue(this.commodityAssortmentQuestion.getName());
		this.fieldPriceBuy.setValue(Float.toString(this.commodityAssortmentQuestion.getPriceBuy()));
		this.fieldPriceSell.setValue(Float.toString(this.commodityAssortmentQuestion.getPriceSell()));
		if(this.commodityAssortmentQuestion.getClassKod()>0){
			this.fieldSelectClass.setSelectedIndex(this.getClassListItemIndex(this.commodityAssortmentQuestion.getClassKod()));
		}
		this.fieldWarranty.setValue(Integer.toString(this.commodityAssortmentQuestion.getWarranty()));
	}
	
	private void saveVisualComponentsToAssortmentCommodity(){
		// this.commodityAssortmentQuestion.setKod(this.fieldKod.getValue());
		// this.commodityAssortmentQuestion.setName(this.fieldName.getValue());
		// this.commodityAssortmentQuestion.setPriceBuy(this.fieldPriceBuy.getValue());
		this.commodityAssortmentQuestion.setPriceSell(this.getFloatFromString(this.fieldPriceSell.getValue()));
		this.commodityAssortmentQuestion.setClassKod(this.getClassKodByIndex(this.fieldSelectClass.getSelectedIndex()));
		try{
			this.commodityAssortmentQuestion.setWarranty(Integer.parseInt(this.fieldWarranty.getValue()));
		}catch(Exception ex){};
	}
	
	/** получить CLASS.KOD на основании выделенного индекса */
	private int getClassKodByIndex(int index){
		return this.fieldListOfClasses.get(index).getKod();
	}
	
	/** получить индекс элемента Select на основании CLASS.KOD */
	private int getClassListItemIndex(int value){
		int returnValue=(-1);
		for(int counter=0;counter<this.fieldListOfClasses.size();counter++){
			if(this.fieldListOfClasses.get(counter).getKod().intValue()==value){
				returnValue=counter;
				break;
			}
		}
		return returnValue;
	}
}
