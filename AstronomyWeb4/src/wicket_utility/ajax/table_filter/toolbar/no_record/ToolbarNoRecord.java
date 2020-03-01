package wicket_utility.ajax.table_filter.toolbar.no_record;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

/** ��� ������� Toolbar, ������� ���������� ����� � ������ �� ������� � ������� ������  */
public class ToolbarNoRecord extends AbstractToolbar{
	private final static long serialVersionUID=1L;
	
	/** ��� ������� Toolbar, ������� ���������� ����� � ������ �� ������� � ������� ������  
	 * @param dataTable - ������� � ������� 
	 * @param text - �����, ������� ������������, � ������ �� ��������� ������ (���-�� �����=0) � ������� 
	 */
	public ToolbarNoRecord(DataTable<?> dataTable, Model<String> text){
		super(dataTable);
		
		WebMarkupContainer toolbarWrap=new WebMarkupContainer("toolbar_wrap");
		this.add(toolbarWrap);
		toolbarWrap.add(new SimpleAttributeModifier("colspan",Integer.toString(dataTable.getColumns().length)));
		
		toolbarWrap.add(new Label("toolbar_text",text));
	}

	@Override
	public boolean isVisible() {
		return this.getTable().getRowCount()==0;
	}
	
}
