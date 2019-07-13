package wicket_utility.ajax.table_filter.toolbar.filter;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.table_filter.toolbar.filter.column.AbstractAjaxFilterColumnPanel;

/** строка в таблице, которая отображает инициированные фильтры для отображения пользователю  */
public class DescriptionFilterToolbar extends AbstractToolbar{
	private final static long serialVersionUID=1L;
	private Model<Boolean> filterIsVisible;
	private AbstractAjaxFilterColumnPanel[] filterColumns;
	private WebMarkupContainer columns;
	private Model<ArrayList<FilterRecord>> modelListRecord=new Model<ArrayList<FilterRecord>>();
	
	/** строка в таблице, которая отображает инициированные фильтры для отображения пользователю  */
	public DescriptionFilterToolbar(DataTable<?> table, 
									AbstractAjaxFilterColumnPanel[] filterColumns,
									Model<Boolean> filterIsVisible){
		super(table);
		this.filterIsVisible=filterIsVisible;
		this.filterColumns=filterColumns;
		columns=new WebMarkupContainer("columns");
		columns.add(new SimpleAttributeModifier("colspan",Integer.toString(table.getColumns().length)));
		this.add(columns);
		columns.add(new ListView<FilterRecord>("filter_row", modelListRecord){
			private final static long serialVersionUID=1L;
			@Override
			protected void populateItem(ListItem<FilterRecord> item) {
				// System.out.println(" Item: "+item.getModelObject().getName());
				FilterRecord model=item.getModelObject();
				item.add(new Label("filter_name", model.getName()));
				item.add(new Label("filter_value",model.getValue()));
			}
		});
		this.updateFilterData();
	}
	
	public void updateFilterData(){
		ArrayList<FilterRecord> listOfRecord=new ArrayList<FilterRecord>();
		if(this.filterColumns!=null){
			for(int counter=0;counter<filterColumns.length;counter++){
				if((filterColumns[counter]!=null)&&(filterColumns[counter].isFilterEnabled())){
					listOfRecord.add(new FilterRecord(filterColumns[counter].getFilterCaption(), 
													  filterColumns[counter].getFilterCaptionValue())
									 );
				}
			}
			// System.out.println("ModelFilter counter:"+listOfRecord.size());
			this.modelListRecord.setObject(listOfRecord);
			// this.modelChanged();
		}else{
			// установить пустые значения в фильтр 
			this.modelListRecord.setObject(listOfRecord);
		}
	}
	
	@Override
	public boolean isVisible() {
		return (this.filterIsVisible.getObject()&&(someFilterActive()));
	}
	
	private boolean someFilterActive(){
		boolean returnValue=false;
		for(int counter=0;counter<filterColumns.length;counter++){
			if((filterColumns[counter]!=null)&&(filterColumns[counter].isFilterEnabled())){
				returnValue=true;
				break;
			}
		}
		return returnValue;
	}
	
	/** отображает фильтр */
	class FilterRecord implements Serializable{
		private final static long serialVersionUID=1L;
		private String name;
		private String value;
		/** отображает фильтр */
		public FilterRecord(String name, String value){
			this.name=name;
			this.value=value;
		}
		public String getName(){
			return this.name;
		}
		public String getValue(){
			return this.value;
		}
	}
}
