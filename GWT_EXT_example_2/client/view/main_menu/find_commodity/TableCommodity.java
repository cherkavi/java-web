package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity;

import com.gwtext.client.core.TextAlign;
import com.gwtext.client.data.MemoryProxy;

import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.FloatFieldDef;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;

public class TableCommodity extends GridPanel{
	/** columns into data for view */
	private final String[] columns=new String[]{"point","name","barCode","serial","price"};
	private FindCommodityConstants constants;
	/** конвертация POJO в Object[][] */
	private Object[][] convertRowDataToArray(RowElement[] rowData){
		Object[][] returnValue=new Object[rowData.length][5];
		for(int counter=0;counter<rowData.length;counter++){
			returnValue[counter][0]=rowData[counter].getPoint();
			returnValue[counter][1]=rowData[counter].getName();
			returnValue[counter][2]=rowData[counter].getBarCode();
			returnValue[counter][3]=rowData[counter].getSerialNumber();
			returnValue[counter][4]=rowData[counter].getPrice();
		}
		return returnValue;
	}
	
	/** обновить данные в таблице */
	public void updateModel(RowElement[] elements){
		this.getStore().setDataProxy(new MemoryProxy(this.convertRowDataToArray(elements)));
		this.getStore().reload();
	}
	
	public TableCommodity(FindCommodityConstants constants){
		this.constants=constants;
		RecordDef recordDef=new RecordDef(new FieldDef[]{
				new StringFieldDef(columns[0]),
				new StringFieldDef(columns[1]),
				new StringFieldDef(columns[2]),
				new StringFieldDef(columns[3]),
				new FloatFieldDef(columns[4])
				
		});
		Store store=new Store(new ArrayReader(recordDef));
		// Grid.column
		ColumnConfig barCodeColumnConfig=new ColumnConfig(this.constants.columnBarCode(),columns[2],80,true);
		barCodeColumnConfig.setAlign(TextAlign.CENTER);
		
		ColumnConfig priceColumnConfig=new ColumnConfig(this.constants.columnPrice(), columns[4], 70, true);
		priceColumnConfig.setAlign(TextAlign.RIGHT);
		
		ColumnConfig[] columnsConfig=new ColumnConfig[]{
				new ColumnConfig(this.constants.columnPoint(),columns[0],120,true),
				new ColumnConfig(this.constants.columnName(),columns[1],300,true),
				barCodeColumnConfig,
				new ColumnConfig(this.constants.columnSerial(),columns[3],100,true),
				priceColumnConfig
		};
		this.setColumnModel(new ColumnModel(columnsConfig));
		// this.addGridCellListener(new GridCellListener(){
		this.setStore(store);
		// this.setTitle(constants.panelTitle());
		this.setStore(store);
		this.setWidth("100%");
		this.setHeight("100%");
		this.setStripeRows(true);
		this.setAutoScroll(true);
		
	}
}
