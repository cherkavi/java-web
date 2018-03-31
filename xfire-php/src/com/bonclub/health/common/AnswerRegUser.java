package com.bonclub.health.common;

/** ������-������� ��� ������� ����������� ������������  */
public class AnswerRegUser {
	private String user;
	private String password;
	private String uniqueId;
	private String urlPdf;
	
	/** ������-������� ��� ������� ����������� ������������  */
	public AnswerRegUser(){
	}

	/** ������-������� ��� ������� ����������� ������������  
	 * @param user - ��� ������������ 
	 * @param password - ������
	 */
	public AnswerRegUser(String user, String password){
		this.user=user;
		this.password=password;
	}

	/** �������� ��� ������������  */
	public String getUser() {
		return user;
	}

	/** ���������� ��� ������������ */
	public void setUser(String user) {
		this.user = user;
	}

	/** �������� ������  */
	public String getPassword() {
		return password;
	}

	/** ���������� ������ */
	public void setPassword(String password) {
		this.password = password;
	}

	/** �������� ���������� ID �� ������� �������  */
	public String getUniqueId() {
		return uniqueId;
	}

	/** ���������� ���������� ID �� ������� �������  */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/** �������� ������ �� PDF */
	public String getUrlPdf() {
		return urlPdf;
	}

	/** ���������� ������ �� PDF */
	public void setUrlPdf(String urlPdf) {
		this.urlPdf = urlPdf;
	}
	
}
