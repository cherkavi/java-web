package database_reflect.database.wrap;

import java.io.Serializable;

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
public class Point_settings implements Serializable {
	@Transient
	private final static long serialVersionUID=1L;
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_POINT_SETTINGS_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
    	private Integer id;

	@Column(name="ID_POINTS")
    	private Integer id_points;

	@Column(name="PRINTER_NAME")
    	private String printer_name;

	@Column(name="PRINTER_BARCODE_NAME")
    	private String printer_barcode_name;

	@Column(name="RECEIPT_TITLE")
    	private String receipt_title;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_points() {
		return id_points;
	}

	public void setId_points(Integer idPoints) {
		id_points = idPoints;
	}

	public String getPrinter_name() {
		return printer_name;
	}

	public void setPrinter_name(String printerName) {
		printer_name = printerName;
	}

	public String getPrinter_barcode_name() {
		return printer_barcode_name;
	}

	public void setPrinter_barcode_name(String printerBarcodeName) {
		printer_barcode_name = printerBarcodeName;
	}

	public String getReceipt_title() {
		return receipt_title;
	}

	public void setReceipt_title(String receiptTitle) {
		receipt_title = receiptTitle;
	}

	
}
