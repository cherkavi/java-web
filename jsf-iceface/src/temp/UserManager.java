package temp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;

import database.Database;
import database.wrap.User;

@ManagedBean(name="user_manager")
public class UserManager {
	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
	
	private String value=sdf.format(new Date());

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public ArrayList<User> getUsers(){
		Connection connection=Database.getInstance().getConnection();
		try{
			ArrayList<User> returnValue=new ArrayList<User>();
			ResultSet rs=connection.createStatement().executeQuery("select * from users");
			while(rs.next()){
				User user=new User(rs.getInt("id"), rs.getString("user_nick"), rs.getString("user_name"), rs.getString("user_password"));
				System.out.println("User:"+user);
				returnValue.add(user);
			}
			return returnValue;
		}catch(Exception ex){
			System.err.println("#getUsers Exception:"+ex.getMessage());
			return null;
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
	}
}
