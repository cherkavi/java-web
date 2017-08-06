package com.cherkashyn.vitalii.web.rest;

import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.exception;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.cherkashyn.vitalii.web.rest.exception.SerializeException;
import com.cherkashyn.vitalii.web.rest.exception.ServiceException;
import com.cherkashyn.vitalii.web.rest.route.ExceptionIntercepter;
import com.cherkashyn.vitalii.web.rest.route.GetTransaction;
import com.cherkashyn.vitalii.web.rest.route.PutTransaction;
import com.cherkashyn.vitalii.web.rest.route.SerializeExceptionIntercepter;
import com.cherkashyn.vitalii.web.rest.route.ServiceExceptionIntercepter;
import com.cherkashyn.vitalii.web.rest.route.SumTransaction;
import com.cherkashyn.vitalii.web.rest.route.TypeTransaction;

import spark.Spark;

@Configuration
@ComponentScan({ "com.cherkashyn.vitalii.web.rest" })
public class App{
	private final static String PARAM_PORT_NUMBER="spark-port";
	
	/**
	 * -Dorg.slf4j.simpleLogger.defaultLogLevel=debug
	 * @param args
	 */
	public static void main(String[] args){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
		init(args);
		setupRoutes(ctx);
        ctx.registerShutdownHook();
	}
	
	private static void init(String[] args) {
		if(System.getProperty(PARAM_PORT_NUMBER)!=null){
			Spark.port(Integer.parseInt(System.getProperty(PARAM_PORT_NUMBER)));
		}
	}


	private static void setupRoutes(ApplicationContext context) {
		/*
		 * put transaction on the server  
		 */
		put("/transactionservice/transaction/:id", context.getBean(PutTransaction.class));

		/*
		 * return one certain transaction
		 */
		get("/transactionservice/transaction/:id", context.getBean(GetTransaction.class));
		
		
		/*
		 * A json list of all transaction ids that share the same type $type.
		 */
		get("/transactionservice/types/:type", context.getBean(TypeTransaction.class));
		

		/*
		 * return sum  A sum of all transactions that are transitively linked by their parent_id to $transaction_id.
		 */
		get("/transactionservice/sum/:id", context.getBean(SumTransaction.class));

		// not found mapping - 404
		get("/*", context.getBean(SumTransaction.class));

		// exception mapping part
		exception(ServiceException.class, context.getBean(ServiceExceptionIntercepter.class));
		exception(SerializeException.class, context.getBean(SerializeExceptionIntercepter.class));
		exception(NumberFormatException.class, context.getBean(SerializeExceptionIntercepter.class));
		
		exception(Exception.class, context.getBean(ExceptionIntercepter.class));
	}

	
}