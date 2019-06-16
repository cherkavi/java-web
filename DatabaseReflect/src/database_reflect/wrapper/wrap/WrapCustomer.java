package database_reflect.wrapper.wrap;
import java.sql.ResultSet;
import java.util.Date;

import database_reflect.database.wrap.Customer;
import database_reflect.wrapper.TableWrap;


public class WrapCustomer extends TableWrap{

	public WrapCustomer() {
		super("customer", "for_send", "id");
	}

	@Override
	public Object getObjectFromResultSet(ResultSet rs) {
		Customer returnValue=new Customer();
		try{
			returnValue.setId(rs.getInt("id"));
			returnValue.setFor_send(rs.getInt("for_send"));
			returnValue.setName(rs.getString("name"));
			returnValue.setSurname(rs.getString("surname"));
			returnValue.setTime_create(new Date(rs.getTime("time_create").getTime()));
		}catch(Exception ex){
			System.err.println("WrapCustomer#getObjectFromResultSet: "+ex.getMessage());
		}
		return returnValue;
	}

}
