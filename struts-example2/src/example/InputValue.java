package example;

import com.opensymphony.xwork2.ActionSupport;

public class InputValue extends ActionSupport {
	private final static long serialVersionUID=1L;
	private String login;
	private String password;
	private String tempProperty;
	
	private void debug(Object information){
		System.out.print("InputValue");
		System.out.print(" DEBUG ");
		System.out.println(information);
	}
	
	@Override
	public String execute() throws Exception{
		System.out.println("execute()");
		// получение сессионного компонента
		// ActionContext.getContext().getSession()
		this.setTempProperty("Login:"+this.getLogin()+"   Password:"+this.getPassword());
		debug("Login:"+this.getLogin());
		debug("Password:"+this.getPassword());
		if((this.getLogin()==null)||(this.getPassword()==null)){
			return SUCCESS;
		}else{
			if((this.getLogin().equals("1"))&&(this.getPassword().equals("1"))){
				return "ok";
			}else{
				return "error";
			}
		}
	}

	public void validate(){
		System.out.println("validate()");
		if((this.getLogin()!=null)&&(this.getPassword()!=null)){
			if(this.getLogin().equals("2")&&this.getPassword().equals("2")){
				// getText() - get value from Properties Text
				addFieldError("password", "Message: password=2 and login=2 >> "+getText("login.required"));
			}else{
				//addActionError("login and password are not recognized");
			}
		}
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

	public String getTempProperty() {
		return tempProperty;
	}

	public void setTempProperty(String tempProperty) {
		this.tempProperty = tempProperty;
	}

	
}
