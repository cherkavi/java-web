package utility;
import java.io.Serializable;
/** класс-обертка для одного элемента из корзины покупателя */
public class TrolleyElement implements Serializable{
	private static final long serialVersionUID=1L;
	private int assortmentKod;
	private String assortmentName;
	private int quantity;
	private double price;
	private double amount;
	
	/** класс-обертка для одного элемента из корзины покупателя 
	 * @param assortment_kod 
	 * @param assortment_name 
	 * @param quantity 
	 * @param price 
	 * @param amount 
	 * */
	public TrolleyElement(int assortment_kod,
						  String assortment_name, 
						  int quantity, 
						  double price, 
						  double amount){
		setAssortmentKod(assortment_kod);
		setAssortmentName(assortment_name);
		setQuantity(quantity);
		setPrice(price);
		setAmount(amount);
	}

	/** класс-обертка для одного элемента из корзины покупателя */
	public TrolleyElement(){
	}

	/** получить код ассортимента */
	public int getAssortmentKod() {
		return assortmentKod;
	}

	/** получить код */
	public void setAssortmentKod(int assortmentKod) {
		this.assortmentKod = assortmentKod;
	}

	/**
	 * @return the assortmentName
	 */
	public String getAssortmentName() {
		return assortmentName;
	}

	/**
	 * @param assortmentName the assortmentName to set
	 */
	public void setAssortmentName(String assortmentName) {
		this.assortmentName = assortmentName;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String toString(){
		return "Assortment_name:"+this.assortmentName+"    Quantity:"+quantity+"   Price:"+price+"   Amount:"+amount;
	}
	
}
