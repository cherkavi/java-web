package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment;

import com.google.gwt.user.client.rpc.IsSerializable;

/** объект, который предназначен для уточнения на клиентской стороне информации по вставке новых позиций <br />
 * клиент должен записать в данный объект цену ( грн ) и код класса 
 *  */
public class CommodityAssortmentQuestion implements IsSerializable{
	/** код товара  */
	private int kod;
	/** наименование товара  */
	private String name;
	/** гарантия */
	private int warranty;
	/** цена закупки ГРН  */
	private float priceBuy;
	/** штрих-код производителя */
	private String barCodeProducer;

	/** цена продажи ГРН */
	private float priceSell;
	/** код класса, который необходим для сохранения ассортимента */
	private int classKod;
	
	public CommodityAssortmentQuestion(){
	}
	
	/** объект, который предназначен для уточнения на клиентской стороне информации по вставке новых позиций <br />
	 * клиент должен записать в данный объект цену ( грн ) и код класса 
	 *  */
	public CommodityAssortmentQuestion(int kod, String name, int warranty, float priceBuy){
		this.kod=kod;
		this.name=name;
		this.warranty=warranty;
		this.priceBuy=priceBuy;
	}

	public int getKod() {
		return kod;
	}

	public String getName() {
		return name;
	}

	public int getWarranty() {
		return warranty;
	}

	/** цена закупки в ГРН */
	public float getPriceBuy() {
		return priceBuy;
	}

	/** цена продажи в ГРН */
	public float getPriceSell() {
		return priceSell;
	}

	public void setPriceSell(float priceSell) {
		this.priceSell = priceSell;
	}

	public int getClassKod() {
		return classKod;
	}

	public void setClassKod(int classKod) {
		this.classKod = classKod;
	}
	
	/** получить код производителя  */
	public String getBarCodeProducer() {
		return barCodeProducer;
	}

	/** установить код произовдителя */
	public void setBarCodeProducer(String barCodeProducer) {
		this.barCodeProducer = barCodeProducer;
	}

	@Override
	public boolean equals(Object value) {
		if(value==null){
			return false;
		}
		if(value instanceof CommodityAssortmentQuestion){
			return ((CommodityAssortmentQuestion)value).getKod()==this.getKod();
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.kod;
	}

	public void setWarranty(Integer value) {
		this.warranty=value;
	}
}
