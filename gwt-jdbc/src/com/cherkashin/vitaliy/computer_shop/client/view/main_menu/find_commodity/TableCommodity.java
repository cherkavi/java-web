package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity;

import java.util.ArrayList;
import java.util.List;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;


public class TableCommodity extends LayoutContainer{
	/** columns into data for view */
	private final String[] columnsName=new String[]{"point","name","barCode","serial","price"};
	private final int[] columnsWidth=new int[]{      200,    300,      90,     90,   50};
	private FindCommodityConstants constants=GWT.create(FindCommodityConstants.class);

	/** получить конфигурацию столбцов */
	private List<ColumnConfig> getColumns(){
		ArrayList<ColumnConfig> returnValue=new ArrayList<ColumnConfig>();
		
		ColumnConfig column=new ColumnConfig();
		column.setId(columnsName[0]);
		column.setHeader(this.constants.columnPoint());
		column.setWidth(columnsWidth[0]);
		column.setSortable(true);
		column.setResizable(true);
		returnValue.add(column);
		
		column=new ColumnConfig();
		column.setId(columnsName[1]);
		column.setHeader(this.constants.columnName());
		column.setWidth(columnsWidth[1]);
		column.setSortable(true);
		column.setResizable(true);
		returnValue.add(column);
		
		column=new ColumnConfig();
		column.setId(columnsName[2]);
		column.setHeader(this.constants.columnBarCode());
		column.setWidth(columnsWidth[2]);
		column.setAlignment(HorizontalAlignment.CENTER);
		column.setSortable(true);
		column.setResizable(true);
		returnValue.add(column);
		
		column=new ColumnConfig();
		column.setId(columnsName[3]);
		column.setHeader(this.constants.columnSerial());
		column.setWidth(columnsWidth[3]);
		column.setSortable(true);
		column.setResizable(true);
		returnValue.add(column);

		column=new ColumnConfig();
		column.setId(columnsName[4]);
		column.setHeader(this.constants.columnPrice());
		column.setWidth(columnsWidth[4]);
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setSortable(true);
		column.setResizable(true);
		returnValue.add(column);

		return returnValue;
	}
	
	private ListStore<RowElementModel> store=null;
	private Grid<RowElementModel> grid;
		
	public TableCommodity(){
		this.setLayout(new FitLayout());
		this.setSize(RootComposite.getWindowWidth(), RootComposite.getWindowWidth());
		store=new ListStore<RowElementModel>();
		
		ColumnModel columnModel=new ColumnModel(this.getColumns());
		
		grid=new Grid<RowElementModel>(store, columnModel);
		this.add(grid);
	}
	
	
	
	/** обновить данные в таблице на основании новых элементов 
	 * @param elements - элементы, которые нужно заменить на текущие в модели 
	 * */
	public void updateModel(RowElement[] elements){
		store.removeAll();
		for(int counter=0;counter<elements.length;counter++){
			store.add(convertToRowElementModel(elements[counter]));
		}
	}

	/** получить {@link RowElementModel} из {@link RowElement} */
	private RowElementModel convertToRowElementModel(RowElement element){
		RowElementModel returnValue=new RowElementModel(element);
		return returnValue;
	}
}
