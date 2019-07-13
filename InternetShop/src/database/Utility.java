package database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import database.wrap.Assortment;
import database.wrap.ClientOrder;
import database.wrap.Clients;

import gui.find_commodity.Filter;

/** класс предназначен для предоставления данных из базы данных*/
public class Utility {
	static Logger field_logger;
	static{
		// TODO конфигурирование логгера
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.ERROR);
		field_logger=Logger.getLogger("Utility");
	}
	
	// отладка работы с базой данных  
	public static void main(String[] args){
		field_logger.debug("begin:");
		Logger.getRootLogger().setLevel(Level.DEBUG);
		/*List list=getListOfClass();
		field_logger.debug("list size:"+list.size());
		for(int counter=0;counter<list.size();counter++){
			field_logger.debug(counter+":"+list.get(counter));
		}*/
		/*
		field_logger.debug("ClassId:"+getClassId("Аксессуар"));
		List<Object[]> list=getCommodityList(new Filter("d","",0.0f,0.0f),null);
		field_logger.debug("List count:"+list.size());
		*/
		/*
		Assortment assortment=Utility.getAssortmentByKod("820");
		field_logger.debug("Assortment:"+assortment);
		*/
		/*
		Object value=getObjectFromTableFromField("PRICE","KOD",new Integer(4050),"PRICE");
		field_logger.debug("Value: "+value+"  Class:"+value.getClass());
		*/
		/*
		 // show parameter too long - java.sql.DataTruncation
			System.out.println("Client_kod:"+saveClients("012345678912", "1@com.ua", "5553322", "this is address", "Name", "Surname", "Father_name"));
		*/
		System.out.println("Client_kod:"+saveClients("0123456789", "1@com.ua", "5553322", "this is address", "Name", "Surname", "Father_name"));
		field_logger.debug("end:");
		
	}
	/** вывести отладочную информацию */
	private static void out_debug(String value){
		System.out.print("Utility");
		System.out.print(" DEBUG ");
		System.out.println(value);
	}
	/** вывести информацию об ошибке */
	private static void out_error(String value){
		System.out.print("Utility");
		System.out.print(" ERROR ");
		System.out.println(value);
	}
	
	/** закрыть сессию */
	public static void closeSession(Session session){
		try{
			//session.disconnect(); // next command automatic disconnect
			if(session!=null){
				session.close();
				session=null;
			}
		}catch(Exception ex){
			out_error("closeSession Exception:"+ex.getMessage());
		}
	}
	
	/** получить список всех классов в виде строки 
	 * @return  с наполненными данными, либо без данных ( в случае ошибки обработки )
	 * */
	@SuppressWarnings("unchecked")
	public static List getListOfClass(){
		List return_value=null;
		Session session=null;
		try{
			session=Connector.getSession();
			//return_value=session.createCriteria(database.wrap.Class.class).addOrder(Order.asc("name")).list();
			return_value=session.createSQLQuery("SELECT NAME FROM CLASS ORDER BY NAME ASC").list();
			return_value.add(0," ");
		}catch(Exception ex){
			out_error("getListOfClass: "+ex.getMessage());
		}finally{
			if(session!=null){
				closeSession(session);
			}
		}
		return (return_value==null)?(new ArrayList()):return_value;
	}
	
	/**
	 * получить уникальный номер Class.id по указанному имени Class.Name
	 * @param name значение Class.Name
	 */
	public static int getClassId(String name){
		int return_value=(-1);
		Session session=null;
		try{
			session=Connector.getSession();
			return_value=((database.wrap.Class)session.createCriteria(database.wrap.Class.class).add(Restrictions.like("name", name)).uniqueResult()).getKod();
		}catch(Exception ex){
			field_logger.error("Exception:"+ex.getMessage());
		}finally{
			closeSession(session);
		}
		return return_value;
	}
	
	/** получить данные в виде результата выборки данных 
	 * @param filter - объект, который получен из панели фильтрации 
	 * @return List(Object[])
	 * <table border=1>
	 * 	<tr><td>0</td><td>COMMODITY_SERIAL_NUMBER</td><tr>
	 * 	<tr><td>1</td><td>CLASS_NAME</td><tr>
	 * 	<tr><td>2</td><td>ASSORTMENT_NAME</td><tr>
	 * 	<tr><td>3</td><td>PRICE_PRICE</td><tr>
	 * 	<tr><td>4</td><td>WARRANTY_MONTH</td><tr>
	 * </table>
	 */
	@SuppressWarnings("unchecked")
	public static List<Object[]> getCommodityList(Filter filter,SortParam sort_param){
		
		List<Object[]> return_value=null;
		String filter_name=filter.getCommodity_name(); 
		int filter_class_id=filter.getClass_id(); 
		float filter_price_min=filter.getPrice_min(); 
		float filter_price_max=filter.getPrice_max();		
		try{
			try{
				filter_name=(filter_name==null)?"":filter_name.trim().toUpperCase();
			}catch(Exception ex){
			}
			
			// check parameter's
			if(filter_price_min<0){
				throw new Exception("Минимальная цена отрицательна ");
			}
			if(filter_price_max<0){
				throw new Exception("Максимальная цена отрицательна ");
			}
			if(filter_price_max<filter_price_min){
				throw new Exception("Максимальная цена меньше минимальной");
			}
			// execute query
			Session session=null;
			try{
				StringBuffer text_query=new StringBuffer();
				text_query.append("SELECT\n");
				text_query.append("ASSORTMENT.KOD ASSORTMENT_KOD,\n");
				text_query.append("CLASS.NAME CLASS_NAME,\n");
				text_query.append("ASSORTMENT.NAME ASSORTMENT_NAME,\n");
				text_query.append("round_money(PRICE.PRICE * 6.4) PRICE_PRICE,\n");
				text_query.append("ASSORTMENT.WARRANTY_MONTH WARRANTY_MONTH\n");
				text_query.append("	FROM COMMODITY \n");
				text_query.append("INNER JOIN ASSORTMENT ON commodity.ASSORTMENT_KOD=ASSORTMENT.KOD \n");
				text_query.append("LEFT JOIN PRICE ON price.kod=ASSORTMENT.PRICE_KOD AND price.valid=1 \n");
				text_query.append("INNER JOIN CLASS ON class.kod=ASSORTMENT.CLASS_KOD \n");
				text_query.append("INNER JOIN SERIAL ON SERIAL.KOD=COMMODITY.SERIAL_KOD \n");
				text_query.append("WHERE --COMMODITY.POINT_KOD=1 AND \n");
				text_query.append("COMMODITY.DATE_IN_OUT<=:date_sells \n");
				if(!filter_name.equals("")){
					text_query.append(" AND RUPPER(ASSORTMENT.NAME) LIKE :filter_name \n");
				}
				if(filter_class_id>0){
					text_query.append(" AND CLASS.KOD=:filter_class_id \n");
				}
				if(filter_price_min>0){
					text_query.append(" AND ROUND_MONEY(PRICE.PRICE*6.4)>=:filter_price_min \n");
				}
				if(filter_price_max>0){
					text_query.append(" AND ROUND_MONEY(PRICE.PRICE*6.4)<=:filter_price_max \n");
				}
				text_query.append(" GROUP BY \n");
				text_query.append("  CLASS.NAME , \n");
				text_query.append("  ASSORTMENT.NAME , \n");
				text_query.append("  ASSORTMENT.KOD , \n");
				text_query.append("  price.PRICE_BUYING, \n");
				text_query.append("  price.PRICE , \n");
				text_query.append("  --COMMODITY.SERIAL_KOD, \n");
				text_query.append("  --SERIAL.NUMBER, \n");
				text_query.append("  --SERIAL.NUMBER_SELLER, \n");
				text_query.append("  ASSORTMENT.WARRANTY_MONTH \n");
				text_query.append("HAVING SUM(COMMODITY.QUANTITY)>0 \n");
				if(sort_param!=null){
					text_query.append("ORDER BY "+sort_param.getProperty());
					if(sort_param.isAscending()){
						text_query.append(" ASC");
					}else{
						text_query.append(" DESC");
					}
				}
				session=Connector.getSession();
				SQLQuery query=session.createSQLQuery(text_query.toString());
				// FIXME Error: query.setParameter("currency",6.4);
				query.setDate("date_sells", new Date());
				//query.setParameter("date_sells", "12.12.2008");
				if(!filter_name.equals("")){
					query.setString("filter_name","%"+filter_name+"%");
				}
				if(filter_class_id>0){
					query.setParameter("filter_class_id", filter_class_id);
				}
				if(filter_price_min>0){
					query.setParameter("filter_price_min",filter_price_min);
				}
				if(filter_price_max>0){
					query.setParameter("filter_price_max",filter_price_max);
				}
				return_value=query.list();
			}catch(Exception ex){
				out_error("getCommodityList Exception:"+ex.getMessage());
			}
			finally{
				if(session!=null){
					closeSession(session);
				}
			}
		}catch(Exception ex){
			out_debug("getCommodityList parameters exception:"+ex.getMessage());
		}
		return (return_value==null)?(new ArrayList<Object[]>()):return_value; 
	}

	public static Assortment getAssortmentByKod(String kod){
		Assortment return_value=null;
		Session session=null;
		try{
			session=Connector.getSession();
			return_value=(Assortment)session.createCriteria(Assortment.class).add(Restrictions.eq("kod", Integer.parseInt(kod))).uniqueResult();
		}catch(Exception ex){
			out_error("getAssortmentByKod Exception: "+kod);
		}finally{
			if(session!=null){
				closeSession(session);
			}
		}
		return return_value;
	}
	
	/** 
	 * Получить из таблицы, имея имя и значение одного из полей, получить другое поле
	 * @param table_name - имя таблицы 
	 * @param field_source_name - имя поля-источника
	 * @param field_source_value - значение поля источника 
	 * @param field_destination_name - имя поля-приемника, значение из которого нужно получить
	 * @return
	 */
	public static Object getObjectFromTableFromField(String table_name, 
													 String field_source_name, 
													 Object field_source_value, 
													 String field_destination_name){
		Object return_value=null;
		Session session=null;
		try{
			session=Connector.getSession();
			SQLQuery query=session.createSQLQuery("SELECT "+field_destination_name+" FROM "+table_name+" WHERE "+field_source_name+"=:value");
			query.setParameter("value", field_source_value);
			return_value=query.uniqueResult();
		}catch(Exception ex){
			out_error("getObjectFromTableFormField: "+ex.getMessage());
		}finally{
			if(session!=null){
				closeSession(session);
			}
		}
		return return_value;
	}

	/** сохранить нового пользователя по введенным данным и получить его уникальный код
	 * @return 0, если данные не сохранены ( произошла ошибка сохранения данных )
	 * */
	public static int saveClients(String bar_code,
							      String email,
							      String phone,
							      String address,
							      String name,
							      String surname,
							      String father_name){
		int return_value=0;
		Session session=null;
		try{
			session=Connector.getSession();
			Clients clients=new Clients();
			clients.setBar_code(bar_code);
			clients.setEmail(email);
			clients.setPhone(phone);
			clients.setAddress(address);
			clients.setName(name);
			clients.setSurname(surname);
			clients.setFather_name(father_name);
			Transaction transaction=session.beginTransaction();
			transaction.begin();
			session.save(clients);
			//Object value=session.save(clients);
			// value - is Primary Key
			//System.out.println("Value:"+value+"    Class:"+value.getClass());
			transaction.commit();
			return_value=clients.getKod();
		}catch(Exception ex){
			
		}finally{
			if(session!=null){
				closeSession(session);
			}
		}
		return return_value;
	}
	
	/** сохранить заказ по данному клиенту
	 * @param client_id - уникальный идентификатор клиента 
	 * @param array_of_order - массив из элементов заказов 
	 */
	public static boolean saveOrder(ClientOrder[] array_of_order){
		boolean return_value=false;
		Session session=null;
		Transaction transaction=null;
		try{
			session=Connector.getSession();
			transaction=session.beginTransaction();
			return_value=true;
			for(int counter=0;counter<array_of_order.length;counter++){
				Object result=session.save(array_of_order[counter]);
				if(result instanceof Integer){
					if( ((Integer)result).intValue()==0 ){
						return_value=false;
						out_error("saveOrder: error in save ClientOrder:"+counter+"    Value:"+array_of_order[counter]);
						break;
					}
				}else{
					out_error("saveOrder: return value is not Integer:"+result.getClass().getName());
				}
			}
			if(return_value=true){
				transaction.commit();
			}else{
				transaction.rollback();
			}
		}catch(Exception ex){
			try{
				if(transaction!=null){
					transaction.rollback();
				}
			}catch(Exception ex2){};
			out_error("saveOrder is Exception:"+ex.getMessage());
		}finally{
			closeSession(session);
		}
		return return_value;
	}
	
	/** получить список всех получателей сообщения по его уникальному коду */
/*	@SuppressWarnings("unchecked")
	public static List<Users> getUsersByMessageSend(int message_send_id){
		out_debug("getUsersByMessageSend: "+message_send_id);
		List<Users> return_value=null;
		Session session=Connector.getSession();
		out_debug("getUsersByMessageSend: Session:"+session);
		try{
			List list=session.createSQLQuery("select {u.*} " +
											 "from reference_recipient " +
											 "inner join users {u} on u.id=reference_recipient.id_recipient " +
											 "where reference_recipient.id_message_send="+message_send_id
											 ).addEntity("u",Users.class).list();
			out_debug("getUsersByMessageSend: list is getted");
			return_value=(List<Users>)list;
		}catch(Exception ex){
			out_error("getUsersByMessageSend:"+ex.getMessage());
		}finally{
			closeSession(session);
		}
		if(return_value==null){
			return_value=new ArrayList<Users>();
		};
		return return_value;
	}
*/	
	
}










