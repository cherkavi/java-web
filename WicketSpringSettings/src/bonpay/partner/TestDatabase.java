package bonpay.partner;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import bonpay.partner.database.Connector;

import bonpay.partner.database.wrap.*;

import java.io.File;

public class TestDatabase {
	
	private static void configureLog4J (){
		Properties properties=new Properties();
		properties.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		properties.put("log4j.appender.stdout.Target", "System.out");
		properties.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		properties.put("log4j.appender.stdout.layout.ConversionPattern", "%d{ABSOLUTE} %5p %c{1}:%L - %m%n");
		properties.put("log4j.rootLogger","debug, stdout");
		properties.put("log4j.logger.org.hibernate","warn");
		properties.put("log4j.logger.org.hibernate","warn, stdout"); 
		properties.put("log4j.logger.org.hibernate.SQL","warn, stdout");
		properties.put("log4j.logger.org.hibernate.type","warn, stdout");
		properties.put("log4j.logger.org.hibernate.engine.QueryParameters","warn"); 
		PropertyConfigurator.configure(properties);
	}
	
	public static void main(String[] args){
		System.out.println("begin");
		String pathToDatabase="D:\\eclipse_workspace\\BonPay\\DataBase\\bonpay.gdb";
		configureLog4J();
		try{
			Connector connector=new Connector(new File(pathToDatabase));
			Session session=connector.openSession();
			@SuppressWarnings("unchecked")
			List<Satellite> list=(List<Satellite>)session.createCriteria(Satellite.class).add(Restrictions.eq("id", new Integer(1))).list();
			for(int counter=0;counter<list.size();counter++){
				System.out.println(counter+":"+list.get(counter).getName());
			}
		}catch(Exception ex){
			System.err.println("HibernateConnection is Exception: "+ex.getMessage());
		}
		System.out.println("end");
	}
}
