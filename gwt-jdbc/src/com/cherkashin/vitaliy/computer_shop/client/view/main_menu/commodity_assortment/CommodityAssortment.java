package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment;

import java.util.ArrayList;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.MainMenu;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment.window_set_price.WindowSetPrice;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.ListBox;

/** Загрузка ассортимента  */
public class CommodityAssortment extends LayoutContainer{
	private CommodityAssortmentConstants constants=GWT.create(CommodityAssortmentConstants.class);
	/** уникальный идентификатор файла, значение данного элемента содержит имя файла, которое нужно читать */
	private Hidden fileId;
	// private SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	private MessageBox waitUpload;
	/** серверное имя файла  */
	private String serverFileName=null;
	/** форма, которая содержит и UploadForm (сам файл) и HiddenTextField (уникальный идентификатор данного файла)*/
	private FormPanel form;
	/**  таблица, которая содержит возвращаемые результаты */
	private FlexTable table;
	/** combobox-ы, которые определяют значения для выделенных столбцов  */
	private ListBox[] listOfListBoxes=null;
	private String[] choiceColumnsNames=new String[]{"", "Firm code", "Name", "Warranty", "Price"};
	private TextField<String> margeTextField=null;
	private ArrayList<ClassIdentifier> listOfClass;
	private Button buttonUploadFile;
	
	/** управляющий элемент для удаленного чтения файла */
	private ICommodityAssortmentManagerAsync manager=GWT.create(ICommodityAssortmentManager.class);
	
	/** получить строку из случайных 16-ых чисел заданной длинны */
	private String getRandomString(int length){
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<length;counter++){
			returnValue.append(Integer.toHexString(Random.nextInt(16)));
		}
		return returnValue.toString();
	}
	
	/** Загрузка ассортимента  */
	public CommodityAssortment(){
		initComponents();
		this.waitUpload=MessageBox.wait("", "wait", null);
		this.manager.getClassMap(new AsyncCallback<ArrayList<ClassIdentifier>>(){
			@Override
			public void onFailure(Throwable caught) {
				waitUpload.close();
				MessageBox.alert("Error","Server does not response",null);
			}

			@Override
			public void onSuccess(ArrayList<ClassIdentifier> result) {
				waitUpload.close();
				Info.display("", "loaded");
				listOfClass=result;
			}
		});
	}
	
	private void initComponents(){
		this.setLayout(new BorderLayout());
		this.setWidth(RootComposite.getWindowWidth());
		this.setHeight(RootComposite.getWindowHeight());
		
		this.table=new FlexTable();
		this.table.setStyleName("flex_table_css");
		this.table.clear(true);
		LayoutContainer gridContainer=new LayoutContainer();
		gridContainer.setScrollMode(Scroll.AUTO);
		gridContainer.setLayout(new FitLayout());
		gridContainer.add(this.table);
		// gridContainer.setHeight(RootComposite.getWindowHeight()-100);
		// this.table.setHeight((RootComposite.getWindowHeight()-100)+"px");

		this.add(gridContainer, new BorderLayoutData(LayoutRegion.CENTER));
		
		Button buttonMainMenu=new Button("Close"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onClick(ComponentEvent ce) {
				onMainMenu();
			}
		};
		this.add(buttonMainMenu, new BorderLayoutData(LayoutRegion.SOUTH,30));
		
		
		LayoutContainer panelManager=new LayoutContainer();
		this.add(panelManager, new BorderLayoutData(LayoutRegion.NORTH,100));
		panelManager.setWidth(200);
		HBoxLayout managerLayout=new HBoxLayout();
		managerLayout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		managerLayout.setPadding(new Padding(5));
		panelManager.setLayout(managerLayout);
		
		form=new FormPanel(){
			@Override
			public void onFrameLoad() {
				super.onFrameLoad();
				// System.out.println("on Frame load:");
				waitUpload.close();
				afterFormUpload();
			}
		};
		panelManager.add(form);
		form.setEncoding(Encoding.MULTIPART);
		// form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(Method.POST);
		// form.setMethod(FormPanel.METHOD_POST);
		form.setAction(GWT.getModuleBaseURL()+"CommodityAssortmentXlsUpload");
		
		FileUploadField fileUpload=new FileUploadField();
		form.add(fileUpload);
		fileUpload.setFieldLabel("File upload");
		fileUpload.setAllowBlank(false);
		fileUpload.setValidator(new Validator(){
			@Override
			public String validate(Field<?> field, String value) {
				System.out.println("Field: "+field.getName()+"   Value:"+value);
				if(value!=null){
					return null;
				}else{
					return "choose file for upload";
				}
			}
		});
		// formPanel.setWidget(fileUpload);
		// formPanel.add(fileUpload);
		fileUpload.setName("file_body");
		
		fileId=new Hidden();
		fileId.setName("file_id");
		// fileId.setValue(this.getTempFileName());
		form.add(fileId);
		
		buttonUploadFile=new Button(constants.captionButtonLoadFile()){
			protected void onClick(com.extjs.gxt.ui.client.event.ComponentEvent ce) {
				onButtonUpload();
			};
		};
		panelManager.add(buttonUploadFile);
		
		margeTextField=new TextField<String>();
		margeTextField.setValue("30");
		margeTextField.setValidator(new Validator(){
			@Override
			public String validate(Field<?> field, String value) {
				if(!((value==null)||(value.equals("")))){
					try{
						int intValue=Integer.parseInt(value);
						if(intValue<=0){
							return "value must be positive";
						}else{
							if(intValue>100){
								return "value must be less 100 ( this is percent )";
							}else{
								return null;
							}
						}
					}catch(Exception ex){
						return "Input integer value";
					}
					
				}else{
					return "Enter value";
				}
			}
		});
		VerticalPanel panelMarge=new VerticalPanel();
		panelMarge.add(new Label("Marge percent"));
		panelMarge.add(margeTextField);
		panelMarge.setBorders(true);
		panelManager.add(panelMarge);
		
		Button buttonSave=new Button(constants.captionButtonSave()){
			protected void onClick(com.extjs.gxt.ui.client.event.ComponentEvent ce) {
				onButtonSave(null);
			};
		};
		buttonSave.setHeight(50);
		buttonSave.setBorders(true);
		panelManager.add(buttonSave);
	}
	
	/** реакция на загрузку данных от удаленной формы  */
	private void afterFormUpload(){
		// загрузить данные в Grid 
		this.waitUpload.close();
		this.waitUpload=MessageBox.wait("", "Server Exchange", "wait");
		manager.getTableData(this.serverFileName, new AsyncCallback<ArrayList<String[]>>(){
			@Override
			public void onFailure(Throwable caught) {
				waitUpload.close();
				MessageBox.alert("Error", "Server Exchange Error", null);
			}

			@Override
			public void onSuccess(ArrayList<String[]> result) {
				waitUpload.close();
				refreshTable(result);
				buttonUploadFile.setEnabled(false);
			}
			
		});
	}
	
	/** наполнить таблицу данными, полученными от сервера */
	private void refreshTable(ArrayList<String[]> data){
		int maxRow=0;
		this.table.clear(true);
		for(int rowCounter=0;rowCounter<data.size();rowCounter++){
			String[] row=data.get(rowCounter);
			if(maxRow<row.length){
				maxRow=row.length;
			}
			for(int columnCounter=0;columnCounter<row.length;columnCounter++){
				this.table.setHTML(rowCounter+1, columnCounter, row[columnCounter]);
			}
		}
		// наполнить Combobo-ами 
		listOfListBoxes=new ListBox[maxRow];
		
		ChangeHandler changeHandler=new ChangeHandler(){
			public void onChange(ChangeEvent event){
				CommodityAssortment.this.onColumnTypeChanged(event);
			}
		};
		for(int columnCounter=0;columnCounter<maxRow;columnCounter++){
			listOfListBoxes[columnCounter]=new ListBox(true);
			for(int counter=0;counter<this.choiceColumnsNames.length;counter++){
				listOfListBoxes[columnCounter].addItem(this.choiceColumnsNames[counter]);
			}
			listOfListBoxes[columnCounter].setVisibleItemCount(this.choiceColumnsNames.length);
			listOfListBoxes[columnCounter].addChangeHandler(changeHandler);
			listOfListBoxes[columnCounter].setSelectedIndex(0);
			this.table.setWidget(0, columnCounter, listOfListBoxes[columnCounter]);
		}
	}
	
	private void onButtonUpload(){
		// System.out.println("CommodityAssortment >>> before submit");
		if(form.isValid()){
			System.out.println("button Save Click");
			this.serverFileName=getRandomString(10);
			fileId.setValue(this.serverFileName);
			form.submit();
			waitUpload=MessageBox.wait("", "Wait", "server exchange");
		}else{
			MessageBox message=new MessageBox();
			message.setClosable(true);
			message.setMessage("Check your's upload file");
			message.show();
		}
		// GWT.log("button save click:", null);
		// System.out.println("CommodityAssortment >>> after submit");
	}
	
	/** загрузить данные */
	private void onButtonSave(ArrayList<CommodityAssortmentQuestion> listResponse){
		System.out.println("OnButton save");
		if(this.table.getRowCount()>1){
			// проверить, указаны ли все столбцы для данного файла
			String[] fieldNames=new String[this.choiceColumnsNames.length-1];
			for(int counter=1;counter<this.choiceColumnsNames.length;counter++){
				fieldNames[counter-1]=this.choiceColumnsNames[counter];
			}
			int[] fieldPosition=new int[this.choiceColumnsNames.length-1];
			for(int counter=0;counter<fieldPosition.length;counter++)fieldPosition[counter]=(-1);
			
			for(int counter=0;counter<this.listOfListBoxes.length;counter++){
				if(this.listOfListBoxes[counter].getSelectedIndex()>0){
					int selectedIndex=this.listOfListBoxes[counter].getSelectedIndex()-1;
					fieldPosition[selectedIndex]=counter;
				}
			}
			int controlAmount=0;
			for(int counter=0;counter<fieldPosition.length;counter++){
				if(fieldPosition[counter]>=0)controlAmount++;
			}
			if(controlAmount==fieldPosition.length){
				this.waitUpload=MessageBox.wait("", "Data processing", "wait");
				float margeValue=Float.parseFloat(this.margeTextField.getValue());
				// передать на сервер столбцы и название файла для парсинга
				this.manager.prepareData(this.serverFileName, 
										 fieldNames, 
										 fieldPosition, 
										 margeValue, 
										 listResponse, 
										 new AsyncCallback<ArrayList<CommodityAssortmentQuestion>>(){
					@Override
					public void onFailure(Throwable caught) {
						CommodityAssortment.this.waitUpload.close();
						MessageBox.alert("warnings", "Server exchange error", null);
					}

					@Override
					public void onSuccess(ArrayList<CommodityAssortmentQuestion> result) {
						waitUpload.close();
						if(result==null){
							MessageBox.alert("", "Process Error", null);
						}else{
							if(result.size()==0){
								Info.display("Notify","Process Done");
								CommodityAssortment.this.table.clear(true);
							}else{
								Info.display("Notify","Need enter the Price of the sell");
								processPriceSell(result);
							}
						}
					}
				});
			}else{
				MessageBox.alert("Warnings", "Set all columns", null);
			}
		}else{
			MessageBox.alert("Warning", "upload file for processing", null);
		}
	}
	
	private ArrayList<CommodityAssortmentQuestion> processListOfQuestion=null;
	private int processListIndex=0;
	
	/** процесс установки продажных цен  */
	private void processPriceSell(ArrayList<CommodityAssortmentQuestion> listOfQuestion){
		this.processListOfQuestion=null;
		this.processListIndex=0;
		if(listOfQuestion.size()>0){
			this.processListOfQuestion=listOfQuestion;
			this.processListIndex=(-1);
			nextProcessPriceSellIteration();
		}
	}
	
	public void nextProcessPriceSellIteration(){
		this.processListIndex++;
		if(this.processListIndex>=this.processListOfQuestion.size()){
			onButtonSave(this.processListOfQuestion);
		}else{
			WindowSetPrice price=new WindowSetPrice(this.listOfClass, this.processListOfQuestion.get(processListIndex), this);
			price.show();
		}
	}
	
	/** реакция на произошедшее изменение в указании типа колонки */
	private void onColumnTypeChanged(final ChangeEvent event){
		// просканировать все элементы на наличие повторений 
		final ListBox currentListBox=(ListBox)event.getSource();
		int selectedIndex=currentListBox.getSelectedIndex();
		for(int counter=0;counter<this.listOfListBoxes.length;counter++){
			if(this.listOfListBoxes[counter].equals(currentListBox)){
				// self
			}else{
				final ListBox anotherBox=this.listOfListBoxes[counter];
				if((selectedIndex!=0)&&(selectedIndex==this.listOfListBoxes[counter].getSelectedIndex())){
					MessageBox.confirm("Warning", 	
									   "Already selected - clear another ?", 
									   new Listener<MessageBoxEvent>(){
						@Override
						public void handleEvent(MessageBoxEvent be) {
							Button button=be.getButtonClicked();
							if(button.getItemId().equalsIgnoreCase("yes")){
								anotherBox.setSelectedIndex(0);
							}else{
								currentListBox.setSelectedIndex(0);
							}
						}
					});
					break;
				}
			}
		}
	}
	
	/** переход в главное меню  */
	private void onMainMenu(){
		RootComposite.showView(new MainMenu());
	}
}

