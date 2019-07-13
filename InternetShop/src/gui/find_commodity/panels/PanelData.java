package gui.find_commodity.panels;

import java.util.Iterator;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import utility.WicketUtility;

import database.Utility;

import gui.find_commodity.Filter;

/** ������, ������� ���������� ������ �� ��������� ������� */
public class PanelData extends Panel{
	private static final long serialVersionUID=1L;
	private DataProvider field_data_provider;
	/** ��������, ������� ����������� ������ ������ */
	private WebPage field_parent_page;
	public PanelData(WebPage parent_page, String id){
		super(id);
		this.field_parent_page=parent_page;
		initComponents();
	}
	
	/** �������������� ������������� ����������� */
	private void initComponents(){
		// table_data
			// CLASS_NAME
			// ASSORTMENT_NAME
			// PRICE_PRICE
			// WARRANTY_MONTH
/*		IColumn[] columns={
				new PropertyColumn(new Model("�����"),"class_name"),
				new PropertyColumn(new Model("������������"),"assortment_name"),
				new PropertyColumn(new Model("����"),"price_price"),
				new PropertyColumn(new Model("��������"),"warranty_month")
		};
*/
		field_data_provider=new DataProvider();
		DefaultDataTable data_table=new DefaultDataTable("data_table",this.getColumns(),field_data_provider,10){

			private static final long serialVersionUID=1L;
			/** TR ��������������� ������, ������� ���������� ������ � ������� */
			@Override
			protected Item newRowItem(String id, int index, IModel model) {
				Item item=super.newRowItem(id, index, model);
				// get style
				String style_string=WicketUtility.getStyleString(item);
				if((index%2)==1){
					style_string=WicketUtility.addStyleElement(style_string, "background-color", "green");
					style_string=WicketUtility.addStyleElement(style_string, "color", "white");
				}else{
					style_string=WicketUtility.addStyleElement(style_string, "background-color", "blue");
					style_string=WicketUtility.addStyleElement(style_string, "color", "white");
				}
				// remove style
				WicketUtility.removeSimpleAttributeModifier(item, "style");
				// set style
				item.add(new SimpleAttributeModifier("style",style_string));
				//System.out.println("item:"+item+" id:"+id+"   index:"+index+"   model:"+model);
				return item;
			}
			
			/** TD ��������������� ������, ������� ���������� ������ � ������� ��� ������ ������ */
			@Override
			protected Item newCellItem(String id, int index, IModel model) {
				Item item=super.newCellItem(id, index, model);
				if(index==2){
					String style_string=WicketUtility.getStyleString(item);
					style_string=WicketUtility.addStyleElement(style_string, "width", PanelData.this.getColumnPriceWidth());
					style_string=WicketUtility.addStyleElement(style_string, "color", PanelData.this.getColumnPriceColor());
					//WicketUtility.removeSimpleAttributeModifier(item, "style");
					item.add(new SimpleAttributeModifier("style",style_string));
					item.add(new SimpleAttributeModifier("align",PanelData.this.getColumnPriceAlign()));
					// item.add(new SimpleAttributeModifier("style","")); // he is override STYLE attribute
				}
				if(index==3){
					String style_string=WicketUtility.getStyleString(item);
					style_string=WicketUtility.addStyleElement(style_string, "width", PanelData.this.getColumnWarrantyWidth());
					style_string=WicketUtility.addStyleElement(style_string, "color", PanelData.this.getColumnWarrantyColor());
					//WicketUtility.removeSimpleAttributeModifier(item, "style");
					item.add(new SimpleAttributeModifier("style",style_string));
					item.add(new SimpleAttributeModifier("align",PanelData.this.getColumnWarrantyAlign()));
					// item.add(new SimpleAttributeModifier("style","")); // he is override STYLE attribute
				}
				
				return item; 
			}
		};
		this.add(data_table);
	}
	
	private String getColumnPriceWidth(){
		return "75";
	}
	
	private String getColumnPriceAlign(){
		return "right";
	}
	
	private String getColumnPriceColor(){
		return "yellow";
	}
	
	private String getColumnWarrantyWidth(){
		return "75";
	}
	private String getColumnWarrantyAlign(){
		return "center";
	}
	private String getColumnWarrantyColor(){
		return "white";
	}
	
	/** ������� ������ �� �������, ������� ����� ���������� */
	private IColumn[] getColumns(){
		IColumn[] columns={
				new AbstractColumn(new Model("�����"),"1"){
					private static final long serialVersionUID=1L;
					@Override
					public void populateItem(Item cellItem, String id, IModel row_model){
						String value;
						try{
							value=((Object[])row_model.getObject())[1].toString();
						}catch(Exception ex){
							value="";
						}
						cellItem.add(new Label(id,new Model(value)));
					}
				},
				
				new AbstractColumn(new Model("������������"),"2"){
					private static final long serialVersionUID=1L;
					@Override
					public void populateItem(Item cellItem, String id, IModel row_model){
						// ������ �� ���������� ������������� ������� ������ 
						String model_id;
						try{
							model_id=((Object[])row_model.getObject())[0].toString();
						}catch(Exception ex){
							model_id="";
						}
						// ������ �� ������������ ������� ������
						String value;
						try{
							value=((Object[])row_model.getObject())[2].toString();
						}catch(Exception ex){
							value="";
						}
						//cellItem.add(new Label(id,new Model(value)));
						cellItem.add(new PanelDataLink(PanelData.this.field_parent_page,id,model_id, value));
					}
				},
				
				new AbstractColumn(new Model("����"),"3"){
					private static final long serialVersionUID=1L;
					@Override
					public void populateItem(Item cellItem, String id, IModel row_model){
						String value;
						try{
							value=((Object[])row_model.getObject())[3].toString();
						}catch(Exception ex){
							value="";
						}
						Label label=new Label(id,new Model(value));
						cellItem.add(label);
					}
				},
				
				new AbstractColumn(new Model("��������"),null){
					private static final long serialVersionUID=1L;
					@Override
					public void populateItem(Item cellItem, String id, IModel row_model){
						String value;
						try{
							value=((Object[])row_model.getObject())[4].toString();
						}catch(Exception ex){
							value="";
						}
						cellItem.add(new Label(id,new Model(value)));
					}
				}
		};
		return columns;
	}
	
	/** ���������� ������ ��� ����������� ������ */
	public void setFilter(Filter filter) throws Exception{
		this.field_data_provider.setFilter(filter);
	}
}

/** �����, ���������� ������, ����������� ��� �����������  */
class DataProvider extends SortableDataProvider{
	private static void out_debug(String information){
		System.out.print("DataProvider");
		System.out.print(" DEBUG ");
		System.out.println(information);
	}
	
	private static final long serialVersionUID=1L;
	/** ������, ������, �� �������� ����� �������� ������ */
	private Filter field_filter=new Filter();
	/** ����� ���-�� ������� � ������� */
	private int field_row_count=0;
	
	/** ������� ������-������� ��� ����������� ������ */
	public DataProvider(){
	}
	
	/** ���������� ����� ������ ��� ������ */
	public void setFilter(Filter filter) throws Exception{
		this.field_filter=filter;
		// TODO ����� ��� ����������� ������� 
		this.field_row_count=Utility.getCommodityList(filter,null).size();
		if(this.field_row_count==0){
			throw new Exception("List is empty");
		}
	}
	
	/** �������� �������� ��� ���������� ��������� */
	@SuppressWarnings("unchecked")
	@Override
	public Iterator iterator(int first, int count) {
		try{
			return Utility.getCommodityList(field_filter,this.getSort()).subList(first, first+count).iterator();
		}catch(Exception ex){
			return null;
		}
	}

	/** �������� ������� ������ �� ��������� �������� List.get()*/
	@Override
	public IModel model(Object row) {
		try{
			return new Model((Object[])row);
		}catch(Exception ex){
			out_debug("model Exception: "+ex.getMessage());
			return null;
		}
	}
	
	/** ������� ����� ���-�� ������� (�����) ������ */
	@Override
	public int size() {
		return this.field_row_count;
	}
	
}
