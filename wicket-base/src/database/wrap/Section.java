package database.wrap;

import javax.persistence.*;

@Entity
@Table(name="SECTION")
public class Section {
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_SECTION_ID")
	@GeneratedValue(generator="generator", strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	@Column(name="ID_PARENT")
	private Integer idParent;
	@Column(name="NAME",length=100)
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdParent() {
		return idParent;
	}
	public void setIdParent(Integer idParent) {
		this.idParent = idParent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
