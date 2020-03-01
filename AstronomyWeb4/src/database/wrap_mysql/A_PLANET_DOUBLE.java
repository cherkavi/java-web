package database.wrap_mysql;

import javax.persistence.*;

/**
 * <table> 
 * 	<tr><th>POJO</th> <th>Database</th></tr>
 * 	<tr><td>id</td> <td>ID</td></tr>
 * 	<tr><td>idPlanetOne</td> <td>ID_PLANET_ONE</td></tr>
 * 	<tr><td>idPlanetTwo</td> <td>ID_PLANET_TWO</td></tr>
 * 	<tr><td>name</td> <td>NAME</td></tr>
 * 	<tr><td>fileComment</td> <td>FILE_COMMENT</td></tr>
 * 	<tr><td>maxPeriod</td> <td>MAX_PERIOD</td></tr>
 * 	<tr><td>stepCalculation</td> <td>STEP_CALCULATION</td></tr>
 * </table>
 */
@Entity
@Table(name="a_planet_double")
public class A_PLANET_DOUBLE {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	@Column(name="id_planet_one")
	private int idPlanetOne;
	@Column(name="id_planet_two")
	private int idPlanetTwo;
	@Column(name="name")
	private String name;
	@Column(name="file_comment")
	private String fileComment;
	@Column(name="max_period")
	private Integer maxPeriod;
	@Column(name="step_calculation")
	private Integer stepCalculation;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPlanetOne() {
		return idPlanetOne;
	}

	public void setIdPlanetOne(int idPlanetOne) {
		this.idPlanetOne = idPlanetOne;
	}

	public int getIdPlanetTwo() {
		return idPlanetTwo;
	}

	public void setIdPlanetTwo(int idPlanetTwo) {
		this.idPlanetTwo = idPlanetTwo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileComment() {
		return fileComment;
	}

	public void setFileComment(String fileComment) {
		this.fileComment = fileComment;
	}

	public Integer getMaxPeriod() {
		return maxPeriod;
	}

	public void setMaxPeriod(Integer maxPeriod) {
		this.maxPeriod = maxPeriod;
	}

	public Integer getStepCalculation() {
		return stepCalculation;
	}

	public void setStepCalculation(Integer stepCalculation) {
		this.stepCalculation = stepCalculation;
	}

	
}
