package database.wrap;
import javax.persistence.*;

@Entity
@Table(name="USER_ADMIN")
public class UserAdmin {
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_USER_ADMIN_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	@Column(name="NAME",length=100)
	private String name;
	@Column(name="USER_LOGIN",length=100)
	private String userLogin;
	@Column(name="USER_PASSWORD",length=100)
	private String userPassword;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
}
