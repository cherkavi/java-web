package database.wrap_mysql;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="a_user_id")
public class A_USER_ID {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="ID_PARTNER")
	private Integer idPartner;
	
	@Column(name="USER_IDENTIFIER")
	private String userIdentifier;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="SURNAME")
	private String surname;
	
	@Column(name="BIRTHDAY")
	private Date birthday;
	
	@Column(name="BIRTHDAY_ID_CITY")
	private Integer birthdayIdCity;
	
	@Column(name="SEX")
	private Integer sex;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserIdentifier() {
		return userIdentifier;
	}

	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getBirthdayIdCity() {
		return birthdayIdCity;
	}

	public void setBirthdayIdCity(Integer birthdayIdCity) {
		this.birthdayIdCity = birthdayIdCity;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getIdPartner() {
		return idPartner;
	}

	public void setIdPartner(Integer idPartner) {
		this.idPartner = idPartner;
	}
	
}
