package database.wrap;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CLIENT_ORDER")
public class ClientOrder {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_CLIENT_ORDER_ID")	
	@GeneratedValue(strategy=GenerationType.AUTO,generator="generator")	
	@Column(name="KOD")
	private int kod;
	@Column(name="KOD_CLIENTS")
	private int kod_clients;
	@Column(name="KOD_ASSORTMENT")
	private int kod_assortment;
	@Column(name="QUANTITY")
	private int quantity;
	@Column(name="PRICE")
	private float price;
	@Column(name="DATE_WRITE")
	private Date date_write;
	
	public ClientOrder(){
		
	}
	
	public ClientOrder(int kod_clients, 
					   int kod_assortment, 
					   int quantity, 
					   float price, 
					   Date date_write){
		this.kod_clients=kod_clients;
		this.kod_assortment=kod_assortment;
		this.quantity=quantity;
		this.price=price;
		this.date_write=date_write;
	}

	public int getKod() {
		return kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	public int getKod_clients() {
		return kod_clients;
	}

	public void setKod_clients(int kod_clients) {
		this.kod_clients = kod_clients;
	}

	public int getKod_assortment() {
		return kod_assortment;
	}

	public void setKod_assortment(int kod_assortment) {
		this.kod_assortment = kod_assortment;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getDate_write() {
		return date_write;
	}

	public void setDate_write(Date date_write) {
		this.date_write = date_write;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	
}
