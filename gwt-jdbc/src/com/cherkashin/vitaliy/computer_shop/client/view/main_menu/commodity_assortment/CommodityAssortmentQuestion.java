package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment;

import com.google.gwt.user.client.rpc.IsSerializable;

/** ������, ������� ������������ ��� ��������� �� ���������� ������� ���������� �� ������� ����� ������� <br />
 * ������ ������ �������� � ������ ������ ���� ( ��� ) � ��� ������ 
 *  */
public class CommodityAssortmentQuestion implements IsSerializable{
	/** ��� ������  */
	private int kod;
	/** ������������ ������  */
	private String name;
	/** �������� */
	private int warranty;
	/** ���� ������� ���  */
	private float priceBuy;
	/** �����-��� ������������� */
	private String barCodeProducer;

	/** ���� ������� ��� */
	private float priceSell;
	/** ��� ������, ������� ��������� ��� ���������� ������������ */
	private int classKod;
	
	public CommodityAssortmentQuestion(){
	}
	
	/** ������, ������� ������������ ��� ��������� �� ���������� ������� ���������� �� ������� ����� ������� <br />
	 * ������ ������ �������� � ������ ������ ���� ( ��� ) � ��� ������ 
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

	/** ���� ������� � ��� */
	public float getPriceBuy() {
		return priceBuy;
	}

	/** ���� ������� � ��� */
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
	
	/** �������� ��� �������������  */
	public String getBarCodeProducer() {
		return barCodeProducer;
	}

	/** ���������� ��� ������������� */
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
