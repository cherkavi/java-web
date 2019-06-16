package database.wrap;
import javax.persistence.*;

/** модули в системе */
@Entity
@Table(name="module")
public class Module {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	@Column(name="name",length=45)
	private String name;
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
	
	
}
