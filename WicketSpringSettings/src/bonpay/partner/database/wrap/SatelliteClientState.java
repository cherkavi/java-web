package bonpay.partner.database.wrap;
import javax.persistence.*;

@Entity
@Table(name="SATELLITE_CLIENT_STATE")
public class SatelliteClientState {
	@Id
	@Column(name="KOD")
	@SequenceGenerator(name="generator",sequenceName="GEN_SATELLITE_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	private int kod;
	@Column(name="NAME",length=50)
	private int name;
	public int getKod() {
		return kod;
	}
	public void setKod(int kod) {
		this.kod = kod;
	}
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	
	
}
