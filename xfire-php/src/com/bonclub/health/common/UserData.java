package com.bonclub.health.common;

/** объект-обертка для запроса регистрации пользователя  */
public class UserData {
	private String user;
	private String password;
	
	/** объект-обертка для запроса регистрации пользователя  */
	public UserData(){
	}

	/** объект-обертка для запроса регистрации пользователя  
	 * @param user - имя пользователя 
	 * @param password - пароль
	 */
	public UserData(String user, String password){
		this.user=user;
		this.password=password;
	}

	/** получить имя пользователя  */
	public String getUser() {
		return user;
	}

	/** установить имя пользователя */
	public void setUser(String user) {
		this.user = user;
	}

	/** получить пароль  */
	public String getPassword() {
		return password;
	}

	/** установить пароль */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString(){
		return "Login:"+this.user+"   Password:"+this.password;
	}
}
