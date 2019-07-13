package database_reflect.wrapper.wrap;

import java.sql.ResultSet;
import java.util.Date;

import database_reflect.database.wrap.Order_list;
import database_reflect.wrapper.TableWrap;


public class WrapOrderList extends TableWrap{

	public WrapOrderList() {
		super("order_list", "for_send", "id");
	}

	@Override
	public Object getObjectFromResultSet(ResultSet rs) {
		Order_list returnValue=new Order_list();
		try{
			returnValue.setId(rs.getInt("id"));
			returnValue.setId_model(rs.getInt("id_model"));
			returnValue.setTime_create(new Date(rs.getTimestamp("time_create").getTime()));
			returnValue.setTime_get_to_process(new Date(rs.getTimestamp("time_get_to_process").getTime()));
			returnValue.setTime_return_from_process(new Date(rs.getTimestamp("time_return_from_process").getTime()));
			returnValue.setTime_return_to_customer(new Date(rs.getTimestamp("time_return_to_customer").getTime()));
			returnValue.setUnique_number(rs.getInt("unique_number"));
			returnValue.setControl_number(rs.getString("control_number"));
			returnValue.setId_order_group(rs.getInt("id_order_group"));
			returnValue.setFor_send(rs.getInt("for_send"));
		}catch(Exception ex){
			System.err.println("WrapOrderList#getObjectFromResultSet Exception: "+ex.getMessage());
		}
		return returnValue;
	}

}
