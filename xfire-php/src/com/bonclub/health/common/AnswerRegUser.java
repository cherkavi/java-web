package com.bonclub.health.common;

/** объект-обертка для запроса регистрации пользователя  */
public class AnswerRegUser {
	private String user;
	private String password;
	private String uniqueId;
	private String urlPdf;
	
	/** объект-обертка для запроса регистрации пользователя  */
	public AnswerRegUser(){
	}

	/** объект-обертка для запроса регистрации пользователя  
	 * @param user - имя пользователя 
	 * @param password - пароль
	 */
	public AnswerRegUser(String user, String password){
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

	/** получить уникальный ID по данному клиенту  */
	public String getUniqueId() {
		return uniqueId;
	}

	/** установить уникальный ID по данному клиенту  */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/** получить ссылку на PDF */
	public String getUrlPdf() {
		return urlPdf;
	}

	/** установить ссылку на PDF */
	public void setUrlPdf(String urlPdf) {
		this.urlPdf = urlPdf;
	}
	
}
