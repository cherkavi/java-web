package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

// http://java.sun.com/javaee/javaserverfaces/1.1/docs/tlddocs/index.html
@ManagedBean (name="java_bean")
@SessionScoped
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
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String clickToSubmit(){
		return "another_page";
	}
}
