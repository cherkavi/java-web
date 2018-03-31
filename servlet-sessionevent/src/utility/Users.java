package utility;
import java.util.HashMap;


public class Users {
	private static HashMap<String,String> users=new HashMap<String,String>();
	private static HashMap<String,String> userNames=new HashMap<String,String>();
	// INFO место наполнения пользователей 
	static{
		users.put("1", "1");
		users.put("2", "2");
		users.put("3", "3");
		users.put("4", "4");
		
		userNames.put("1", "Valera");
		userNames.put("2", "Sergey");
		userNames.put("3", "Petya");
		userNames.put("4", "Vasya");
	}
	
	/** получить уникальный идентификатор пользователя, на основании логина и пароля */
	public static String getUserId(String login, String password){
		try{
			if(users.get(login).equals(password)){
				return login;
			}else{
				return null;
			}
		}catch(Exception ex){
			return null;
		}
	}
	
	/** получить на основании уникального идентификатора имя пользователя */
	public static String getUserName(String userId){
		try{
			return userNames.get(userId);
		}catch(Exception ex){
			return null;
		}
		
	}
}
