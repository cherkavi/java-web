package database_reflect.server_listener;

import database.wrap.*;
import database_reflect.marshalling.TransportObject;

public class RecordListener implements ITableRecordListener {
	
	public RecordListener(){
		System.out.println("RecordListener was created ");
	}
	
	@Override
	public String getRecord(TransportObject transport) {
		CartridgeModel cartridgeModel=transport.getCartridgeModel();
		System.out.println(cartridgeModel);
		
		CartridgeVendor cartridgeVendor=transport.getCartridgeVendor();
		System.out.println(cartridgeVendor);
		
		Customer customer=transport.getCustomer();
		System.out.println(customer);
		
		OrderGroup orderGroup=transport.getOrderGroup();
		System.out.println(orderGroup);
		
		OrderList orderList=transport.getOrderList();
		System.out.println(orderList);
		
		return "OK";
	}

}
