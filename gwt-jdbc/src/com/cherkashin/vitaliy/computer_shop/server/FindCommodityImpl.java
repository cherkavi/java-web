package com.cherkashin.vitaliy.computer_shop.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity.IFindCommodity;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity.RowElement;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import database.ConnectWrap;
import database.StaticConnector;

/** поиск товара по введенным критериям  */
public class FindCommodityImpl extends RemoteServiceServlet implements IFindCommodity{
	private final static long serialVersionUID=1L;
	
	@Override
	public RowElement[] findCommodity(String name, String barCode, String serial) {
		//System.out.println("Name: "+name);
		//System.out.println("BarCode: "+barCode);
		//System.out.println("Serial: "+serial);
		RowElement[] returnValue=new RowElement[]{};
		if(    ((name==null)||(name.trim().equals("")))
			&&((barCode==null)||(barCode.trim().equals("")))
			&&((serial==null)||(serial.trim().equals("")))
			){
			// нет условий на выборку
			return returnValue; 
		}
		ArrayList<RowElement> list=new ArrayList<RowElement>();
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			float currency=0;
			Connection connection=connector.getConnection();
			ResultSet rs=connection.createStatement().executeQuery(getCurrencyByDate(new Date()));
			if(rs.next()){
				currency=rs.getFloat(1);
			}
			rs.getStatement().close();
			// получить по сегодняшней дате курс валют 
			String query=this.getQueryForParameters(currency, name, barCode, serial);
			//System.out.println(query);
			rs=connection.createStatement().executeQuery(query);
			while(rs.next()){
				list.add(new RowElement(rs.getString(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getFloat(5)));
			}
			returnValue=list.toArray(returnValue);
		}catch(Exception ex){
			System.err.println("FindCommodityImpl#findCommodity Exception: "+ex.getMessage());
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
		try{
			Thread.sleep(5000);
		}catch(Exception ex){};
		return returnValue;
	}
	
	private String getQueryForParameters(float currency, String name, String barCode, String serial){
		StringBuffer where=new StringBuffer();
		if(((name!=null)&&(!name.trim().equals("")))){
			if(where.length()==0){
				where.append("WHERE ");
			}else{
				where.append(" AND ");
			}
			String[] values=name.toUpperCase().split(" ");
			for(int counter=0;counter<values.length;counter++){
				if(counter>0){
					where.append(" AND ");
				}
				where.append("rupper(assortment.name) like '%"+values[counter].toUpperCase()+"%'\n ");
			}
		}

		if(((barCode!=null)&&(!barCode.trim().equals("")))){
			if(where.length()==0){
				where.append("WHERE ");
			}else{
				where.append(" AND ");
			}
			where.append("rupper(SERIAL.NUMBER) like '%"+barCode.toUpperCase()+"%'\n ");
		}

		if(((serial!=null)&&(!serial.trim().equals("")))){
			if(where.length()==0){
				where.append("WHERE ");
			}else{
				where.append(" AND ");
			}
			where.append("rupper(SERIAL.NUMBER_SELLER) like '%"+serial.toUpperCase()+"%'\n ");
		}
		
		if(where.length()==0){
			where.append("WHERE ");
		}else{
			where.append(" AND ");
		}
		where.append("points.kod>0 and points.kod<1000 \n ");
				
		
		StringBuffer returnValue=new StringBuffer();
		returnValue.append("	SELECT	\n");
		returnValue.append("	      points.name point,	\n");
		returnValue.append("	      assortment.name name,	\n");
		returnValue.append("	      SERIAL.NUMBER barcode,	\n");
		returnValue.append("	      SERIAL.NUMBER_SELLER serial,	\n");
		returnValue.append("	      round_money(price.price*"+Float.toString(currency)+") price	\n");
		returnValue.append("	FROM COMMODITY	\n");
		returnValue.append("	inner join assortment on assortment.kod=commodity.assortment_kod	\n");
		returnValue.append("	INNER JOIN SERIAL ON SERIAL.KOD=COMMODITY.SERIAL_KOD	\n");
		returnValue.append("	inner join points on points.kod=commodity.point_kod	\n");
		returnValue.append("	inner join price on price.kod=assortment.price_kod	\n");
		returnValue.append(where);
		returnValue.append("		\n");
		returnValue.append("	GROUP BY	\n");
		returnValue.append("	      SERIAL.NUMBER ,	\n");
		returnValue.append("	      SERIAL.NUMBER_SELLER,	\n");
		returnValue.append("	      points.name,	\n");
		returnValue.append("	      assortment.name,	\n");
		returnValue.append("	      price.price	\n");
		returnValue.append("	HAVING SUM(COMMODITY.QUANTITY)>0	\n");
		
		return returnValue.toString();
	}

	private SimpleDateFormat sqlDateFormat=new SimpleDateFormat("dd.MM.yyyy");
	/** получить запрос на получение по указанной дате курса валют */
	private String getCurrencyByDate(Date date){
		return "select first 1 skip 0 currency_value from course where date_set<='"+sqlDateFormat.format(date)+"' order by kod desc";
		
	}
}
