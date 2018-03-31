package server;

import java.util.Date;

/** описание счета пользователя */
public class Account {
	private String name;
	private Integer uniqueId;
	private String fullOwner;
	private Date dateAccountCreate;
	
	// FIXME IMPORTANT for object must be simple constructor without arguments 
	public Account(){
		
	}
	
	public Account(Integer uniqueId, String name, String fullOwnerName, Date dateAccountCreate){
		this.uniqueId=uniqueId;
		this.name=name;
		this.fullOwner=fullOwnerName;
		this.dateAccountCreate=dateAccountCreate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(Integer uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getFullOwner() {
		return fullOwner;
	}

	public void setFullOwner(String fullOwner) {
		this.fullOwner = fullOwner;
	}

	public Date getDateAccountCreate() {
		return dateAccountCreate;
	}

	public void setDateAccountCreate(Date dateAccountCreate) {
		this.dateAccountCreate = dateAccountCreate;
	}
	
	@Override 
	public String toString(){
		return this.getUniqueId()+" "+this.getName()+" "+this.getName()+" "+this.getDateAccountCreate();
	}
}
