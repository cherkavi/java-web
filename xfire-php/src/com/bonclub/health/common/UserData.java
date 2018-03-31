package com.bonclub.health.common;

/** ������-������� ��� ������� ����������� ������������  */
public class UserData {
	private String user;
	private String password;
	
	/** ������-������� ��� ������� ����������� ������������  */
	public UserData(){
	}

	/** ������-������� ��� ������� ����������� ������������  
	 * @param user - ��� ������������ 
	 * @param password - ������
	 */
	public UserData(String user, String password){
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

	@Override
	public String toString(){
		return "Login:"+this.user+"   Password:"+this.password;
	}
}
