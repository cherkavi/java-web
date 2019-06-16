package database_reflect.wrapper.wrap;
import java.sql.ResultSet;

import database_reflect.database.wrap.Order_group;
import database_reflect.wrapper.TableWrap;


public class WrapOrderGroup extends TableWrap{

	public WrapOrderGroup() {
		super("order_group", "for_send", "id");
	}

	@Override
	public Object getObjectFromResultSet(ResultSet rs) {
		Order_group returnValue=new Order_group();
		try{
			returnValue.setDescription(rs.getString("description"));
			returnValue.setFor_send(rs.getInt("for_send"));
			returnValue.setId(rs.getInt("id"));
			returnValue.setId_customer(rs.getInt("id_customer"));
		}catch(Exception ex){
			System.err.println("WrapOrderGroup#getObjectFromResultSet Exception:"+ex.getMessage());
		}
		return returnValue;
	}

}
