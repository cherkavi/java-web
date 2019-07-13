package database.wrap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="POINTS") 
public class Points {
	@Transient
	private final static long serialVersionUID=1L;
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_POINTS_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
    	private Integer id;

	@Column(name="NAME")
    	private String name;

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

}
