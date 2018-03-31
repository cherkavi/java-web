package com.cherkashyn.vitalii.computer_shop.rediscount.service.point;

import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.computer_shop.rediscount.domain.Points;

@Service
public class PointServiceImpl implements PointService{
	
	private final static String QUERY_TRADE_POINTS="select {points.*} from points where is_trade=1 and kod>0 and name is not null";
	private final static String QUERY_POINT="select {points.*} from points where kod=:kod";
	
	@Autowired(required=true)
	SessionFactory factory;
	
	@Override
	public Points[] getTradePoints() {
		Session session=null;
		try{
			session=factory.openSession();
			session.beginTransaction();
			SQLQuery query=session.createSQLQuery(QUERY_TRADE_POINTS);
			query.addEntity("points", Points.class);
			List<Points> pointList=(List<Points>)query.list();
			return pointList.toArray(new Points[pointList.size()]);
		}finally{
			if(session!=null){
				session.close();
			}
		}
	}

	@Override
	public Points getPointByKod(Integer kod) {
		Session session=null;
		try{
			session=factory.openSession();
			session.beginTransaction();
			SQLQuery query=session.createSQLQuery(QUERY_POINT);
			query.addEntity("points", Points.class);
			query.setParameter("kod", kod);
			try{
				return (Points)query.uniqueResult();
			}catch(NonUniqueResultException ne){
				return null;
			}
		}finally{
			if(session!=null){
				session.close();
			}
		}
	}
	
	

}
