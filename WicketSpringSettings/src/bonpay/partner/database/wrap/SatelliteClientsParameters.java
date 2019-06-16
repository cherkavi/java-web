package bonpay.partner.database.wrap;
import javax.persistence.*;

@Entity
@Table(name="SATELLITE_CLIENTS_PARAMETERS")
public class SatelliteClientsParameters {
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@SequenceGenerator(name="generator",sequenceName="GEN_PARAMETERS_ID")
	private int id;
	@Column(name="ID_SATELLITE_CLIENTS")
	private int idSatelliteClients;
	@Lob	
	@Column(name="PARAMETER_VALUE")
	private byte[] parameter;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdSatelliteClients() {
		return idSatelliteClients;
	}
	public void setIdSatelliteClients(int idSatelliteClients) {
		this.idSatelliteClients = idSatelliteClients;
	}
	public byte[] getParameter() {
		return parameter;
	}
	public void setParameter(byte[] parameter) {
		this.parameter = parameter;
	}
	/** очистить все параметры */
	public void clear(){
		this.id=0;
		this.idSatelliteClients=0;
		this.parameter=null;
	}
	
}
