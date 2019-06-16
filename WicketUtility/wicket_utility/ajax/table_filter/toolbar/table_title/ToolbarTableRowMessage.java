package wicket_utility.ajax.table_filter.toolbar.table_title;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

/** строка в таблице, которая может быть добавлена как {@link DataTable#addTopToolbar} или же  {@link DataTable#addBottomToolbar} */
public class ToolbarTableRowMessage extends AbstractToolbar{
	private final static long serialVersionUID=1L;
	
	/** строка в таблице, которая может быть добавлена как {@link DataTable#addTopToolbar} или же  {@link DataTable#addBottomToolbar} 
	 * @param dataTable - таблица, к которой будет происходить присоединение 
	 * @param modelTitle - модель заголовка, который будет отображаться
	 */
	public ToolbarTableRowMessage(DataTable<?> dataTable, Model<String> modelTitle) {
		// super(DataTable.TOOLBAR_COMPONENT_ID); is inherits from Panel 
		super(dataTable);
		
		WebMarkupContainer titleWrap=new WebMarkupContainer("title_wrap");
		titleWrap.add(new SimpleAttributeModifier("colspan", Integer.toString(dataTable.getColumns().length)));
		this.add(titleWrap);
		
		Label label=new Label("title", modelTitle);
		titleWrap.add(label);
	}

}
