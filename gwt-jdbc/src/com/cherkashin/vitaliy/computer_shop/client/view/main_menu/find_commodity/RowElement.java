package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity;

import com.google.gwt.user.client.rpc.IsSerializable;

/** элемент для передачи клиенту ответа на запрос выборки всех данных 
 * <table>
 * 	<tr> <td> point </td> </tr>
 * 	<tr> <td> name </td> </tr>
 * 	<tr> <td> barCode </td> </tr>
 * 	<tr> <td> serialNumber </td> </tr>
 * 	<tr> <td> price </td> </tr>
 * </table>
 * 
 * */
public class RowElement implements IsSerializable{
	private String point;
	private String name;
	private String barCode;
	private String serialNumber;
	private float price;
	
	/** элемент для передачи клиенту ответа на запрос выборки всех данных */
	public RowElement(){
	}

	/** элемент для передачи клиенту ответа на запрос выборки всех данных 
	 * @param point - имя точки
	 * @param name - наименование товара
	 * @param barCode - код 
	 * @param serialNumber - серийный номер произоводителя 
	 * @param price - цена продажи 
	 */
	public RowElement(String point, String name, String barCode, String serialNumber, float price){
		this.point=point;
		this.name=name;
		this.barCode=barCode;
		this.serialNumber=serialNumber;
		this.price=price;
	}
	
	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
