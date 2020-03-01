package wicket_utility.ajax.table_filter.toolbar.filter.column.select_distinct;

import java.io.Serializable;
import java.util.ArrayList;

/** источник данных для DropDownChoice */
public interface ISelectDataSource extends Serializable{
	/** получить все возможные варианты фильров для установки  */
	public ArrayList<String> getValues();
}
