package com.cherkashyn.vitalii.web.rest;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cherkashyn.vitalii.web.rest.SparkTestUtil.UrlResponse;
import com.cherkashyn.vitalii.web.rest.model.Transaction;
import com.cherkashyn.vitalii.web.rest.transport.DomainSerializer;

public class AppTest {
	private final static int DEFAULT_PORT=4567;
	
	@BeforeClass
	public static void initClass() throws InterruptedException{
		// Spark framework doesn't have approach to test application with Spring container inside
		App.main(new String[]{});
		TimeUnit.SECONDS.sleep(3); // need to replace with 
	}
	
	SparkTestUtil util;
	
	@Before
	public void init(){
		util = new SparkTestUtil(DEFAULT_PORT);
	}
	
	@Test
	public void checkCreation() throws Exception{
		// given
		String url="/transactionservice/transaction/100";
		String requestBody="{ \"amount\":1.2,\"type\":\"test\" }";
		
		// when 
		UrlResponse response=util.doMethod("PUT", url, requestBody);
		Transaction transaction=DomainSerializer.unrender(response.body, Transaction.class);
		
		// then 
		Assert.assertTrue(response.isPositive());
		Assert.assertNotNull(response.body);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(new BigDecimal("1.2"), transaction.getAmount());
		Assert.assertEquals("test", transaction.getType());
		
		// when
		response=util.doMethod("GET", url, null);
		transaction=DomainSerializer.unrender(response.body, Transaction.class);
		
		// then
		Assert.assertTrue(response.isPositive());
		Assert.assertNotNull(response.body);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(new BigDecimal("1.2"), transaction.getAmount());
		Assert.assertEquals("test", transaction.getType());
	}
	
	@Test
	public void checkCreationWithBrokenTest() throws Exception{
		// given
		String url="/transactionservice/transaction/100";
		String requestBody="{ amount:1.2,type:test }}";
		
		// when 
		UrlResponse response=util.doMethod("PUT", url, requestBody);
		
		// then 
		Assert.assertFalse(response.isPositive());
		Assert.assertNotNull(response.body);
	}

	@Test
	public void getTransactionsByType() throws Exception{
		// create new 
		// given
		String url="/transactionservice/transaction/100";
		String requestBody="{ \"amount\":1.2,\"type\":\"test-alt\" }";
		
		// when 
		UrlResponse response=util.doMethod("PUT", url, requestBody);
		Transaction transaction=DomainSerializer.unrender(response.body, Transaction.class);
		
		// then 
		Assert.assertTrue(response.isPositive());
		Assert.assertNotNull(response.body);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(new BigDecimal("1.2"), transaction.getAmount());
		Assert.assertEquals("test-alt", transaction.getType());
		
		// update existing 
		// given
		url="/transactionservice/transaction/100";
		requestBody="{ \"amount\":1.2,\"type\":\"test\" }";
		
		// when 
		response=util.doMethod("PUT", url, requestBody);
		transaction=DomainSerializer.unrender(response.body, Transaction.class);
		
		// then 
		Assert.assertTrue(response.isPositive());
		Assert.assertNotNull(response.body);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(new BigDecimal("1.2"), transaction.getAmount());
		Assert.assertEquals("test", transaction.getType());

		
		// given
		url="/transactionservice/transaction/101";
		requestBody="{ \"amount\":22.2,\"type\":\"test\" }";
		
		// when 
		response=util.doMethod("PUT", url, requestBody);
		transaction=DomainSerializer.unrender(response.body, Transaction.class);
		
		// then 
		Assert.assertTrue(response.isPositive());
		Assert.assertNotNull(response.body);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(new BigDecimal("22.2"), transaction.getAmount());
		Assert.assertEquals("test", transaction.getType());
		

		// given
		url="/transactionservice/transaction/102";
		requestBody="{ \"amount\":15.7,\"type\":\"test-alternative\" }";
		
		// when 
		response=util.doMethod("PUT", url, requestBody);
		transaction=DomainSerializer.unrender(response.body, Transaction.class);
		
		// then 
		Assert.assertTrue(response.isPositive());
		Assert.assertNotNull(response.body);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(new BigDecimal("15.7"), transaction.getAmount());
		Assert.assertEquals("test-alternative", transaction.getType());


		// when 
		response=util.doMethod("GET", "/transactionservice/types/test", null);
		// then 
		Assert.assertTrue(response.body.contains("100"));
		Assert.assertTrue(response.body.contains("101"));

	
		// when 
		response=util.doMethod("GET", "/transactionservice/types/test-alternative", null);
		// then 
		Assert.assertTrue(response.body.contains("102"));

	}

	
	
	@Test
	public void getSumOfTransaction() throws Exception{
		// create new 
		// given
		String url="/transactionservice/transaction/100";
		String requestBody="{ \"amount\":1.2,\"type\":\"test\" }";
		
		// when 
		UrlResponse response=util.doMethod("PUT", url, requestBody);
		Transaction transaction=DomainSerializer.unrender(response.body, Transaction.class);
		
		// then 
		Assert.assertTrue(response.isPositive());
		Assert.assertNotNull(response.body);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(new BigDecimal("1.2"), transaction.getAmount());
		Assert.assertEquals("test", transaction.getType());
		

		// update existing 
		// given
		url="/transactionservice/transaction/101";
		requestBody="{ \"amount\":22.2,\"type\":\"test\", \"parent_id\":\"100\" }";
		
		// when 
		response=util.doMethod("PUT", url, requestBody);
		transaction=DomainSerializer.unrender(response.body, Transaction.class);
		
		// then 
		Assert.assertTrue(response.isPositive());
		Assert.assertNotNull(response.body);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(new BigDecimal("22.2"), transaction.getAmount());
		Assert.assertEquals("test", transaction.getType());
		

		// given
		url="/transactionservice/transaction/102";
		requestBody="{ \"amount\":15.7,\"type\":\"test-alternative\", \"parent_id\":\"100\" }";;
		
		// when 
		response=util.doMethod("PUT", url, requestBody);
		transaction=DomainSerializer.unrender(response.body, Transaction.class);
		
		// then 
		Assert.assertTrue(response.isPositive());
		Assert.assertNotNull(response.body);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(new BigDecimal("15.7"), transaction.getAmount());
		Assert.assertEquals("test-alternative", transaction.getType());


		// when 
		response=util.doMethod("GET", "/transactionservice/sum/100", null);
		// then 
		Assert.assertTrue(response.body.contains("37.9"));

	}

	
	@Test
	public void getSumOfNonExistingTransaction() throws Exception{
		// when 
		UrlResponse response=util.doMethod("GET", "/transactionservice/sum/999", null);
		// then 
		Assert.assertEquals("{\"sum\":0}", response.body);

	}
	
}


