package database_reflect.wrapper.wrap;

import java.sql.ResultSet;

import database_reflect.database.wrap.Cartridge_model;
import database_reflect.wrapper.TableWrap;


public class WrapCartridgeModel extends TableWrap {

	public WrapCartridgeModel() {
		super("cartridge_model", "for_send", "id");
	}

	@Override
	public Object getObjectFromResultSet(ResultSet rs) {
		Cartridge_model returnValue=new Cartridge_model();
		try{
			returnValue.setFor_send(rs.getInt("for_send"));
			returnValue.setId(rs.getInt("id"));
			returnValue.setId_vendor(rs.getInt("id_vendor"));
			returnValue.setName(rs.getString("name"));
			returnValue.setPrice(rs.getFloat("price"));
		}catch(Exception ex){
			System.err.println("WrapCartridgeModel#getObjectFromResultSet: "+ex.getMessage());
		}
		return returnValue;
	}


}
