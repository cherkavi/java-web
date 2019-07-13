package wicket_utility.ajax.table_filter.toolbar.filter;

import java.util.ArrayList;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.table_filter.toolbar.filter.column.AbstractAjaxFilterColumnPanel;

/** ������, ������� �������� ������� ��� ����������� */
public class AjaxHeadersToolbarFilter extends AbstractToolbar{
	private final static long serialVersionUID=1L;
	private ArrayList<AbstractAjaxFilterColumnPanel> listOfElement=new ArrayList<AbstractAjaxFilterColumnPanel>();
	public static final String idColumn="column";
	/** ������ ����������� ��������� ������� ������� */
	private Model<Boolean> isVisible=null;
	
	/** ������, ������� �������� ������� ��� ����������� 
	 * @param dataTable - �������, ������� ����������� ������ ������
	 * @param isVisible - ������, ������� ���������� ����� �� ������������ ������ ������, ��� �� �����  
	 * @param AbstractAjaxFilterColumnPanel - ������ �� ��������, ������� ����� ���������� ��� ������ �� ��������
	 * <br> <b> ���-�� ��������� ������ ��������������� ���-�� �������� � ������� </b>  
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
					// System.out.println("AjaxHeadersToolbarFilter#constructor - ��������� ���������� ���-�� ��������");
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
					// ��� � ������ ������� ���������� 
					item.add(new EmptyPanel(idColumn));
				}else{
					item.add(objectModel);
				}
			}
			
		};
		this.add(listView);
		initComponents();
	}
	
	/** �������������� ������������� �����������  */
	private void initComponents(){
		
	}
	
	@Override
	public boolean isVisible() {
		return this.isVisible.getObject();
	}
}
