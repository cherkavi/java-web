package example;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;



public class Struts2HelloWorld extends ActionSupport {
	private final static long serialVersionUID=1L;

    public static final String MESSAGE = "Struts 2 Hello World Tutorial!";

    
    
    public String execute() throws Exception {
    	System.out.println("execute");
        setMessage(MESSAGE);
        //return SUCCESS;
        return "another_return_value";
    }
    
    private String message;

    public void setMessage(String message){
    	System.out.println("setMessage:"+message);
        this.message = message;
    }

    public String getMessage() {
    	System.out.println("getMessage:");
        return message;
    }

  public String getCurrentTime(){
	  System.out.println("getCurrentTime:");
      return new Date().toString();
  }
}