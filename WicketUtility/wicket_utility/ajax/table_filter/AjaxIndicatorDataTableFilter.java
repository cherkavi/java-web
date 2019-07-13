package wicket_utility.ajax.table_filter;


import org.apache.wicket.ajax.AjaxRequestTarget;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.table_filter.toolbar.filter.AjaxHeadersToolbarFilter;
import wicket_utility.ajax.table_filter.toolbar.filter.DescriptionFilterToolbar;
import wicket_utility.ajax.table_filter.toolbar.filter.column.AbstractAjaxFilterColumnPanel;
import wicket_utility.ajax.table_filter.toolbar.no_record.ToolbarNoRecord;
import wicket_utility.ajax.table_filter.toolbar.table_title.ToolbarTableRowMessage;

/** Ajax �������, ������� �������� ������� � ���������� ( � ������������ �������� ������ �������� ) */
public class AjaxIndicatorDataTableFilter <T> extends DataTable<T>{
	private final static long serialVersionUID=1L;
	DescriptionFilterToolbar descriptionFilterToolbar;
	/** Ajax �������, ������� �������� ������� � ���������� ( � ������������ �������� ������ �������� ) 
	 * @param id - ���������� ������������� ������� �� �������� Wicket 
	 * @param tableTitleHeader - ������, ������� ������ ������������ ������ ��� ������ �������
	 * <br> ���� <b>null</b> �� �� ������ ������ ��� ���������� ������ ���������
	 * @param modelNoRecord - ��������� ������, ������� ������������ � ������ ��������� ������ ������ � �������
	 * @param columns - �������, ������� ������� ���������� � �������
	 * @param filterColumns - ������� ��� �������� 
	 * @param filterIsVisible - ������ �������� ����������� ������� � ���������    
	 * @param dataProvider - ��������� ������  
	 * @param rowsPerPage - ������������ ���-�� ����� �� ����� �������� 
	 * @param ajaxIndicator - ID ��������, ������� ������������ � ������ ���������� XmlHttpRequest ������� �� ������ (� ������������ ���� �� �������� ����� �� ������ ������)
	 */
	public AjaxIndicatorDataTableFilter(String id,
									    Model<String> tableTitleHeader,
									    Model<String> modelNoRecord, 
			                 	  		final IColumn<T>[] columns,
			                 	  		AbstractAjaxFilterColumnPanel[] filterColumns,
			                 	  		Model<Boolean> filterIsVisible, 
			                 	  		SortableDataProvider<T> dataProvider,
			                 	  		int rowsPerPage,
			                 	  		String ajaxIndicator) {
		super(id, columns, dataProvider, rowsPerPage);
        setOutputMarkupId(true);
        setVersioned(false);
        // ��������� �� ������� ���������� ��������� � ������� ����� 
        if(tableTitleHeader!=null){
        	this.addTopToolbar(new ToolbarTableRowMessage(this, tableTitleHeader));
        }
        // �������� ��������� ����������, ������� ����������
        descriptionFilterToolbar=new DescriptionFilterToolbar(this, filterColumns,filterIsVisible);
        addTopToolbar(descriptionFilterToolbar);
        // �������� ��������� ��������� �� ���������  
        addTopToolbar(new AjaxIndicatorNavigatorToolbar<T>(this,ajaxIndicator));
        
        // �������� ���� ������� 
        addTopToolbar(new AjaxFallbackHeadersToolbarIndicator<T>(this, dataProvider, ajaxIndicator));
        
        // �������� ������� ��� �������
        addTopToolbar(new AjaxHeadersToolbarFilter(this,
        										   filterIsVisible,	
        										   filterColumns));
        
        // �������� ������ � ����� �������, ������� ����� ���������������� �� ������� ������� 
        addBottomToolbar(new ToolbarNoRecord(this,modelNoRecord ));
	}

	protected Item<T> newRowItem(String id, int index, IModel<T> model) {
		// System.out.println("newRowItem    Id:"+id+"  Index:"+index+"   Model: "+model.getObject().getClass().getName());
        return new OddEvenItem<T>(id, index, model);
    }

	@Override
	protected void onBeforeRender() {
		// System.out.println("update filter data");
		this.descriptionFilterToolbar.updateFilterData();
		// this.modelChanged();
		super.onBeforeRender();
	}
}

/** ������ ��������� ��� Ajax ������� c ������������ ���������� ��������  */  
class AjaxIndicatorNavigatorToolbar<T> extends NavigationToolbar{
	private final static long serialVersionUID=1L;
	@SuppressWarnings("unused")
	private final String ajaxIndicator; 
	
	/** ������ ��������� ��� Ajax ������� c ������������ ���������� ��������  
	 * @param table - ������� 
	 * @param ajaxIndicator - ��������� ����������� 
	 */
	public AjaxIndicatorNavigatorToolbar(final DataTable<T> table, String ajaxIndicator) {
		super(table);
		this.ajaxIndicator=ajaxIndicator;
	}

	protected PagingNavigator newPagingNavigator(final String navigatorId, final DataTable<?> table) {
		// return new AjaxIndicatorPagingNavigator(navigatorId, table,this.ajaxIndicator);
		return new AjaxPagingNavigator(navigatorId, table) {
			private static final long serialVersionUID = 1L;

			/**
			 * Implement our own ajax event handling in order to update the
			 * datatable itself, as the default implementation doesn't support
			 * DataViews.
			 * 
			 * @see AjaxPagingNavigator#onAjaxEvent(AjaxRequestTarget)
			 */
			protected void onAjaxEvent(AjaxRequestTarget target) {
				// System.out.println("AjaxNavigatorToolbar onAjaxEvent: "+table.getCurrentPage());
				target.addComponent(table);
			}
		};
	}
}


class AjaxFallbackHeadersToolbarIndicator<T> extends AjaxFallbackHeadersToolbar implements IAjaxIndicatorAware{
	private final static long serialVersionUID=1L;
	private String ajaxIndicator;
	
	public AjaxFallbackHeadersToolbarIndicator(DataTable<T> dataTable, 
											   SortableDataProvider<T> dataProvider,
											   String ajaxIndicator){
		super(dataTable,dataProvider);
		this.ajaxIndicator=ajaxIndicator;
	}

	@Override
	public String getAjaxIndicatorMarkupId() {
		return ajaxIndicator;
	}
}


/*
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigation;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationIncrementLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationLink;
 
 *  ��������� �� ��������� c Ajax-�����������  - �� ��������  

class AjaxIndicatorPagingNavigator extends PagingNavigator implements IAjaxIndicatorAware{
	private final static long serialVersionUID=1L;
	//  The pageable component that needs to be updated. 
	private final IPageable pageable;
	 
	private String waitIndicator;

	// ��������� �� ��������� c Ajax-�����������  
	public AjaxIndicatorPagingNavigator(final String id, final IPageable pageable, String waitIndicator) {
		this(id, pageable,waitIndicator, null);
	}

	//  ��������� �� ��������� c Ajax-�����������  
	protected AjaxIndicatorPagingNavigator(final String id, final IPageable pageable, String waitIndicator, final IPagingLabelProvider labelProvider) {
		super(id, pageable,labelProvider);
		this .pageable = pageable;
		this.setOutputMarkupId(true);
		this.waitIndicator=waitIndicator;
	}

	@Override
	public String getAjaxIndicatorMarkupId() {
		return waitIndicator;
	}

	
	protected Link<?> newPagingNavigationIncrementLink(String id,
			IPageable pageable, int increment) {
		return new AjaxPagingNavigationIncrementLink(id, pageable, increment);
	}
	
	protected Link<?> newPagingNavigationLink(String id,IPageable pageable, int pageNumber) {
		return new AjaxPagingNavigationLink(id, pageable, pageNumber);
	}	
	
	protected PagingNavigation newNavigation(final IPageable pageable, final IPagingLabelProvider labelProvider) {
		return new AjaxPagingNavigation("navigation", pageable, labelProvider);
	}
	
	protected void onAjaxEvent(AjaxRequestTarget target) {
		 Component container = ((Component) pageable);
		 while (container instanceof  AbstractRepeater) {
			 container = container.getParent();
		 }
		 target.addComponent(container);
		 if (((MarkupContainer) container).contains(this , true) == false) {
			 target.addComponent(this);
		 }
	}
}
*/