package wicket_utility.ajax.table_filter.toolbar.filter;

import java.util.ArrayList;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.table_filter.toolbar.filter.column.AbstractAjaxFilterColumnPanel;

/** панель, которая содержит фильтры для отображения */
public class AjaxHeadersToolbarFilter extends AbstractToolbar{
	private final static long serialVersionUID=1L;
	private ArrayList<AbstractAjaxFilterColumnPanel> listOfElement=new ArrayList<AbstractAjaxFilterColumnPanel>();
	public static final String idColumn="column";
	/** модель определения видимости данного фильтра */
	private Model<Boolean> isVisible=null;
	
	/** панель, которая содержит фильтры для отображения 
	 * @param dataTable - таблица, которой принадлежит данный фильтр
	 * @param isVisible - модель, которая определяет будет ли отображаться данный фильтр, или не будет  
	 * @param AbstractAjaxFilterColumnPanel - массив из фильтров, которые нужно отобразить под каждым из столбцов
	 * <br> <b> кол-во элементов должно соответствовать кол-ву столбцов в таблице </b>  
	 * */
	public AjaxHeadersToolbarFilter(DataTable<?> dataTable, 
									Model<Boolean> isVisible, 
									AbstractAjaxFilterColumnPanel[] filters){
		super(dataTable);
		this.isVisible=isVisible;
		if(filters==null){
			for(int counter=0;counter<dataTable.getColumns().length;counter++){
				this.listOfElement.add(null);
			}
		}else{
			for(int counter=0;counter<dataTable.getColumns().length;counter++){
				try{
					this.listOfElement.add(filters[counter]);
				}catch(Exception ex){
					// System.out.println("AjaxHeadersToolbarFilter#constructor - проверьте переданное кол-во фильтров");
					this.listOfElement.add(null);
				}
			}
		}
		
		ListView<AbstractAjaxFilterColumnPanel> listView=new ListView<AbstractAjaxFilterColumnPanel>("columns", new Model<ArrayList<AbstractAjaxFilterColumnPanel>>(this.listOfElement)){
			private final static long serialVersionUID=1L;
			@Override
			protected void populateItem(ListItem<AbstractAjaxFilterColumnPanel> item) {
				AbstractAjaxFilterColumnPanel objectModel=item.getModelObject();
				if(objectModel==null){
					// нет в данном столбце фильтрации 
					item.add(new EmptyPanel(idColumn));
				}else{
					item.add(objectModel);
				}
			}
			
		};
		this.add(listView);
		initComponents();
	}
	
	/** первоначальная инициализация компонентов  */
	private void initComponents(){
		
	}
	
	@Override
	public boolean isVisible() {
		return this.isVisible.getObject();
	}
}
