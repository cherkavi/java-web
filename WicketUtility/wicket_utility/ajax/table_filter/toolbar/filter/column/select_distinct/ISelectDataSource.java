package wicket_utility.ajax.table_filter.toolbar.filter.column.select_distinct;

import java.io.Serializable;
import java.util.ArrayList;

/** �������� ������ ��� DropDownChoice */
public interface ISelectDataSource extends Serializable{
	/** �������� ��� ��������� �������� ������� ��� ���������  */
	public ArrayList<String> getValues();
}
