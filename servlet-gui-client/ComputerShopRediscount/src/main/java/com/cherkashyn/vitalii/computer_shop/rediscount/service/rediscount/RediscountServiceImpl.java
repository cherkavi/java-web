package com.cherkashyn.vitalii.computer_shop.rediscount.service.rediscount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.NonUniqueResultException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.computer_shop.rediscount.domain.Assortment;
import com.cherkashyn.vitalii.computer_shop.rediscount.domain.Rediscount;
import com.cherkashyn.vitalii.computer_shop.rediscount.domain.RediscountClientDto;
import com.cherkashyn.vitalii.computer_shop.rediscount.domain.Serial;

@Service
public class RediscountServiceImpl implements RediscountService{
	private final static String GET_LAST_REDISCOUNTED="select first 10 {r.*}, {s.*}, {a.*} from rediscount r inner join serial s on s.kod=r.kod_serial inner join assortment a on a.kod=r.kod_assortment where r.is_source=-1 and kod_point=:kod_point and date_rediscount=:date_rediscount order by r.kod desc";
	private final static String IS_REDISCOUNT_PRESENT="select first 1 kod from rediscount r where r.is_source=1 and kod_point=:kod_point and date_rediscount=:date_rediscount ";
	private final static String GET_LAST_REDISCOUNTED_KOD_POINT="kod_point";
	private final static String GET_LAST_REDISCOUNTED_DATE="date_rediscount";
	
	private final static String GET_REDISCOUNT="select {r.*} from rediscount r inner join serial s on s.kod=r.kod_serial and s.number=:serial_number where kod_point=:kod_point and date_rediscount=:rediscount_date";

	private final static String GET_REDISCOUNT_POINT="kod_point";
	private final static String GET_REDISCOUNT_DATE="rediscount_date";
	private final static String GET_REDISCOUNT_SERIAL="serial_number";
	
	@Autowired
	SessionFactory factory;


	@Override
	public boolean isRediscountPresent(Date date, int pointNumber) {
		Session session=null;
		try{
			session=factory.openSession();
			SQLQuery query=session.createSQLQuery(IS_REDISCOUNT_PRESENT);
			query.setParameter(GET_LAST_REDISCOUNTED_DATE, date);
			query.setParameter(GET_LAST_REDISCOUNTED_KOD_POINT, pointNumber);
			try{
				return query.uniqueResult()!=null;
			}catch(NonUniqueResultException re){
				return true;
			}
		}finally{
			if(session!=null){
				session.close();
			}
		}
	}

	
	@Override
	public RequestResult putRediscount(Date date, int pointNumber, String serialNumber) {
		Session session=null;
		try{
			session=factory.openSession();
			SQLQuery query=session.createSQLQuery(GET_REDISCOUNT);
			query.addEntity("r", Rediscount.class);
			query.setParameter(GET_REDISCOUNT_DATE, date);
			query.setParameter(GET_REDISCOUNT_POINT, pointNumber);
			query.setParameter(GET_REDISCOUNT_SERIAL, serialNumber);
			
			List<Rediscount> rediscountList=(List<Rediscount>)query.list();
			if(!isInRediscount(rediscountList)){
				return RequestResult.NOT_FOUND_IN_SOURCE;
			}
			if(wasCounted(rediscountList)){
				return RequestResult.ALREADY_SCANNED;
			}
			session.beginTransaction();
			// add to Rediscount 
			Rediscount rediscount=new Rediscount();
			rediscount.setDateRediscount(date);
			rediscount.setDateWrite(new Date());
			rediscount.setIsSource((short)-1);
			rediscount.setKodPoint(pointNumber);
			
			rediscount.setKodSerial(rediscountList.get(0).getKodSerial());
			rediscount.setKodAssortment(rediscountList.get(0).getKodAssortment());
			session.persist(rediscount);
			session.getTransaction().commit();
			return RequestResult.ADDED;
		}finally{
			if(session!=null){
				session.close();
			}
		}
	}
	
	private boolean isInRediscount(List<Rediscount> rediscountList) {
		if(rediscountList==null || rediscountList.size()==0){
			return false;
		}
		for(Rediscount each:rediscountList){
			if(each.getIsSource()>0){
				return true;
			}
		}
		return false;
	}

	private boolean wasCounted(List<Rediscount> rediscountList) {
		for(Rediscount each:rediscountList){
			if(each.getIsSource()<0){
				return true;
			}
		}
		return false;
	}

	@Override
	public RediscountClientDto[] getLastRediscounted(Date date, int pointNumber) {
		Session session=null;
		try{
			session=factory.openSession();
			SQLQuery query=session.createSQLQuery(GET_LAST_REDISCOUNTED);
			query.addEntity("r", Rediscount.class);
			query.addEntity("s", Serial.class);
			query.addEntity("a", Assortment.class);
			query.setParameter(GET_LAST_REDISCOUNTED_DATE, date);
			query.setParameter(GET_LAST_REDISCOUNTED_KOD_POINT, pointNumber);
			return  convert((List<Object[]>)query.list());
		}finally{
			if(session!=null){
				session.close();
			}
		}
	}

	
	
	private RediscountClientDto[] convert(List<Object[]> list) {
		List<RediscountClientDto> returnValue=new ArrayList<RediscountClientDto>(list.size());
		for(Object[] eachArray: list){
			returnValue.add(convert(getFromArray(eachArray, Rediscount.class), getFromArray(eachArray, Serial.class), getFromArray(eachArray, Assortment.class)));
		}
		return returnValue.toArray(new RediscountClientDto[returnValue.size()]);
	}

	private RediscountClientDto convert(Rediscount rediscount, Serial serial, Assortment assortment) {
		RediscountClientDto returnValue=new RediscountClientDto();
		returnValue.setName(assortment.getName());
		returnValue.setNumber(serial.getNumber());
		return returnValue;
	}

	private <T> T getFromArray(Object[] array, Class<T> clazz) {
		for(Object each:array){
			if(clazz.isAssignableFrom(each.getClass())){
				return (T)each;
			}
		}
		return null;
	}

	Date clearTime(Date date){
		return DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
	}


}
