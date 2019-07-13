package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

// http://java.sun.com/javaee/javaserverfaces/1.1/docs/tlddocs/index.html
@ManagedBean (name="java_bean")
@RequestScoped
public class TestBean {
	private String caption="Input text value";
	private String value;
	
	public void setValue(String value){
		System.out.println("setValue: "+value);
		this.value=value;
	}
	
	public String getValue(){
		System.out.println("getValue:"+value);
		return value;
	}

	public String getCaption() {
		System.out.println("Get Caption");
		return caption;
	}

	public void setCaption(String caption) {
		System.out.println("Set Caption");
		this.caption = caption;
	}
	
	public String clickToSubmit(){
		return "index";
	}
}
