package wicket_utility.ajax.table;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;

/** Ajax Таблица, которая отличается от {@link AjaxFallbackDefaultDataTable} наличием {@link IAjaxIndicatorAware} при кликах по заголовкам  */
public class AjaxIndicatorDataTable <T> extends DataTable<T>{
	private final static long serialVersionUID=1L;
	
	/** Ajax Таблица, которая отличается от {@link AjaxFallbackDefaultDataTable} наличием {@link IAjaxIndicatorAware} при кликах по заголовкам  */
	public AjaxIndicatorDataTable(String id,
			                 	  final IColumn<T>[] columns, 
			                 	  SortableDataProvider<T> dataProvider,
			                 	  int rowsPerPage,
			                 	  String ajaxIndicator) {
		super(id, columns, dataProvider, rowsPerPage);
        setOutputMarkupId(true);
        setVersioned(false);
        //addTopToolbar(new AjaxNavigationToolbar(this));
        // addTopToolbar(new AjaxFallbackHeadersToolbar(this , dataProvider)); // реализация AjaxFallbackDefaultDataTable
        addTopToolbar(new AjaxIndicatorNavigatorToolbar<T>(this,ajaxIndicator));
        addTopToolbar(new AjaxFallbackHeadersToolbarIndicator<T>(this, dataProvider, ajaxIndicator));

        addBottomToolbar(new NoRecordsToolbar(this ));
        
	}

	protected Item<T> newRowItem(String id, int index, IModel<T> model) {
		// System.out.println("newRowItem    Id:"+id+"  Index:"+index+"   Model: "+model.getObject().getClass().getName());
        return new OddEvenItem<T>(id, index, model);
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


/** панель навигации для Ajax таблицы c отображением индикатора ожидания  */  
class AjaxIndicatorNavigatorToolbar<T> extends NavigationToolbar{
	private final static long serialVersionUID=1L;
	@SuppressWarnings("unused")
	private final String ajaxIndicator; 
	
	/** панель навигации для Ajax таблицы c отображением индикатора ожидания  
	 * @param table - таблица 
	 * @param ajaxIndicator - индикатор отображения 
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
 
 *  навигатор по страницам c Ajax-индикатором  - не работает  

class AjaxIndicatorPagingNavigator extends PagingNavigator implements IAjaxIndicatorAware{
	private final static long serialVersionUID=1L;
	//  The pageable component that needs to be updated. 
	private final IPageable pageable;
	 
	private String waitIndicator;

	// навигатор по страницам c Ajax-индикатором  
	public AjaxIndicatorPagingNavigator(final String id, final IPageable pageable, String waitIndicator) {
		this(id, pageable,waitIndicator, null);
	}

	//  навигатор по страницам c Ajax-индикатором  
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