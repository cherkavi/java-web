package database.wrap;
import javax.persistence.*;

@Entity
@Table(name="state_of_task")
public class StateOfTask {
	@Id
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
