package example.first;

import java.io.Serializable;

public class ShareObject implements Serializable{
	private final static long serialVersionUID=1L;
	private int id;
	private String name;
	
	public ShareObject(){}
	
	public ShareObject(int id, String name){
		this.id=id;
		this.name=name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		
		return this.id+":"+this.name;
	}
}
