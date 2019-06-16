package wicket_utility.table.toolbar.title;

import java.util.ArrayList;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.Model;

import wicket_utility.panel_label.PanelLabel;

/** ѕанель с заголовками, которые представлены модел€ми */
public class TitleToolbar extends AbstractToolbar{
	private final static long serialVersionUID=1L;
	private boolean visible=true;
	private Model<ArrayList<TitleToolbarModel>> modelList=new Model<ArrayList<TitleToolbarModel>>(null);
	
	/** ѕанель с заголовками, которые представлены модел€ми 
	 * <br>
	 * <small> например дл€ отображени€ суммами  </small>
	 * @param table - таблица дл€ отображени€ данных 
	 * @param cssStyle - (nullable) —SS стиль
	 * @param models - (nullable) модели, которые будут отображатьс€ в заголовках (th)
	 */
	public TitleToolbar(DataTable<?> table,
						String cssStyle,
						TitleToolbarModel[] models) {
		super(table);
		
		if(models!=null){
			ListView<TitleToolbarModel> toolbarWrap=new ListView<TitleToolbarModel>("toolbar_wrap",modelList){
				private final static long serialVersionUID=1L;
				@Override
				protected void populateItem(ListItem<TitleToolbarModel> item) {
					TitleToolbarModel model=item.getModelObject();
					if(model!=null){
						//System.out.println("toolbar: ");
						item.add(new PanelLabel("toolbar_text", model.getModel(),model.getHorizontaAlignment()));
					}else{
						item.add(new EmptyPanel("toolbar_text"));
					}
				}
			};
			this.add(toolbarWrap);
			// наполнить список данными 
			ArrayList<TitleToolbarModel> list=new ArrayList<TitleToolbarModel>(table.getColumns().length);
			//System.out.println("table RowCount:"+table.getRowCount());
			for(int counter=0;counter<table.getColumns().length;counter++){
				if(models.length>counter){
					// System.out.println("Models:"+models[counter]);
					list.add(models[counter]);
				}else{
					list.add(null);
				}
			}
			//System.out.println("List Count:"+list.size());
				// установить 
			this.modelList.setObject(list);
		}else{
			WebMarkupContainer toolbar=new WebMarkupContainer("toolbar_wrap");
			toolbar.add(new EmptyPanel("toolbar_text"));
			this.visible=true;
		}
	}

	public Model<ArrayList<TitleToolbarModel>> getModelList() {
		return modelList;
	}

	public void setModelList(Model<ArrayList<TitleToolbarModel>> modelList) {
		this.modelList = modelList;
	}

	@Override
	public boolean isVisible() {
		if(this.visible==true){
			if(this.getTable().getRowCount()>0){
				return true;
			}else {
				return false;
			}
		}else{
			return false;
		}
	}
}
