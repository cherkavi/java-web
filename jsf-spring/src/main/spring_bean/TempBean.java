package main.spring_bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("spring_bean")
@Scope("prototype")
public class TempBean {
	public TempBean(){
	}
	
	private String name="this is test Spring value";
	
	public TempBean(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		this.name=newName;
	}

}
