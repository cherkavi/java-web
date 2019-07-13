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
@Table(name="POINT_SETTINGS") 
public class PointSettings {
	@Transient
	private final static long serialVersionUID=1L;
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_POINT_SETTINGS_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
    	private Integer id;

	@Column(name="ID_POINTS")
    	private Integer idPoints;

	@Column(name="PRINTER_NAME")
    	private String printer;

	@Column(name="PRINTER_BARCODE_NAME")
    	private String printerBarcode;

	@Column(name="RECEIPT_TITLE")
    	private String receiptTitle;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdPoints() {
		return idPoints;
	}

	public void setIdPoints(Integer idPoints) {
		this.idPoints = idPoints;
	}

	public String getPrinter() {
		return printer;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	public String getPrinterBarcode() {
		return this.printerBarcode;
	}

	public void setPrinterBarcode(String printerBarcode) {
		this.printerBarcode = printerBarcode;
	}

	public String getReceiptTitle() {
		return receiptTitle;
	}

	public void setReceiptTitle(String receiptTitle) {
		this.receiptTitle = receiptTitle;
	}

	
}
