package database_reflect.marshalling;

import java.io.Serializable;
import database.wrap.*;

/** объект, который будет передаваться по сети, для переноса объектов внутри себя */
public class TransportObject implements Serializable{
	private static final long serialVersionUID=1L;

	private CartridgeModel cartridgeModel;
	private CartridgeVendor cartridgeVendor;
	private Customer customer;
	private OrderGroup orderGroup;
	private OrderList orderList;
	public CartridgeModel getCartridgeModel() {
		return cartridgeModel;
	}
	public void setCartridgeModel(CartridgeModel cartridgeModel) {
		this.cartridgeModel = cartridgeModel;
	}
	public CartridgeVendor getCartridgeVendor() {
		return cartridgeVendor;
	}
	public void setCartridgeVendor(CartridgeVendor cartridgeVendor) {
		this.cartridgeVendor = cartridgeVendor;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public OrderGroup getOrderGroup() {
		return orderGroup;
	}
	public void setOrderGroup(OrderGroup orderGroup) {
		this.orderGroup = orderGroup;
	}
	public OrderList getOrderList() {
		return orderList;
	}
	public void setOrderList(OrderList orderList) {
		this.orderList = orderList;
	}
	
	
}
