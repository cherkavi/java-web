package database.wrap;


public class User {
	private int id;
	private String nick;
	private String login;
	private String password;
	
	
	public User(){
	}
	
	public User(int id, String nick, String login, String password) {
		super();
		this.id = id;
		this.nick = nick;
		this.login = login;
		this.password = password;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString(){
		StringBuilder returnValue=new StringBuilder();
		returnValue
		.append("========================")
		.append("Id:"+this.id).append("\n")
		.append("Nick:"+this.nick).append("\n")
		.append("Login:"+this.login).append("\n")
		.append("Password:"+this.password).append("\n")
		.append("-------------------");
		
		return returnValue.toString();
	}
}
