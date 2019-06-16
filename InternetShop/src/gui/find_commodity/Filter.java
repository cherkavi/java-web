package gui.find_commodity;
import java.io.Serializable;

/** объекты, которые содержат все данные по возможным вариантам фильтра по обмену данными */
public class Filter implements Serializable{
	private static final long serialVersionUID=1L;
	/** часть строки, которая содержится в наименовании */
	private String commodity_name;
	/** уникальный идентификатор класса, который содержится в базе данных*/
	private String class_name;
	/** минимальная цена */
	private float price_min;
	/** максимальная цена */
	private float price_max;

	public Filter(String commodity_name, String class_name, float price_min, float price_max){
		setCommodity_name(commodity_name);
		setClass_name(class_name);
		setPrice_min(price_min);
		setPrice_max(price_max);
	}
	
	public Filter (){
		
	}
	
	public String getCommodity_name() {
		return commodity_name;
	}
	public void setCommodity_name(String commodity_name) {
		this.commodity_name = commodity_name;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public float getPrice_min() {
		return price_min;
	}
	public void setPrice_min(float price_min) {
		this.price_min = price_min;
	}
	public float getPrice_max() {
		return price_max;
	}
	public void setPrice_max(float price_max) {
		this.price_max = price_max;
	}
	
	public int getClass_id(){
		int return_value=(-1);
		if((this.class_name!=null)&&(!this.class_name.trim().equals(""))){
			return_value=database.Utility.getClassId(this.class_name);
		}
		return return_value;
	}
	
	public String toString(){
		return "Class:"+getClass_id()+"   commodity_name:"+commodity_name+"    price_min:"+price_min+"   price_max:"+price_max;
	}
}
