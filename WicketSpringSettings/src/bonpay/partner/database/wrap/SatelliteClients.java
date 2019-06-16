package bonpay.partner.database.wrap;
import javax.persistence.*;

@Entity
@Table(name="SATELLITE_CLIENTS")
public class SatelliteClients {
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@SequenceGenerator(name="generator",sequenceName="GEN_SATELLITE_ID")
	private int id;
	@Column(name="SATELLITE_SESSION_ID",length=50)
	private String satelliteSessionId;
	@Column(name="SATELLITE_ID")
	private Integer satelliteId;
	@Column(name="CLIENT_STATE")
	private int clientState;
	@Column(name="BONPAY_SESSION_ID",length=50)
	private String bonpaySessionId;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSessionId() {
		return satelliteSessionId;
	}
	public void setSessionId(String sessionId) {
		this.satelliteSessionId = sessionId;
	}
	public Integer getSatelliteId() {
		return satelliteId;
	}
	public void setSatelliteId(Integer satelliteId) {
		this.satelliteId = satelliteId;
	}
	public int getClientState() {
		return clientState;
	}
	public void setClientState(int clientState) {
		this.clientState = clientState;
	}
	public String getSatelliteSessionId() {
		return satelliteSessionId;
	}
	public void setSatelliteSessionId(String satelliteSessionId) {
		this.satelliteSessionId = satelliteSessionId;
	}
	public String getBonpaySessionId() {
		return bonpaySessionId;
	}
	public void setBonpaySessionId(String bonpaySessionId) {
		this.bonpaySessionId = bonpaySessionId;
	}
	
}
