package com.cherkashyn.vitalii.web.rest.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.cherkashyn.vitalii.web.rest.model.Transaction;


public class ParentIdMemoryTransactionStorageTest {
	
	@Test
	public void checkCRUD(){
		// given
		ParentIdMemoryTransactionStorage storage=new ParentIdMemoryTransactionStorage();
		
		Transaction transaction=new Transaction();
		transaction.setId(100L);
		transaction.setParentId(101L);
		transaction.setType("test-type");
		transaction.setAmount(new BigDecimal("10.01"));
		
		// when
		storage.save(transaction);
		
		// then
		Transaction valueFromStorage=storage.read(transaction.getParentId()).get(0);
		Assert.assertNotNull(valueFromStorage);
		Assert.assertEquals(transaction, valueFromStorage);
		Assert.assertEquals(transaction.getParentId(), valueFromStorage.getParentId());
		Assert.assertEquals(transaction.getType(), valueFromStorage.getType());
		Assert.assertEquals(transaction.getParentId(), valueFromStorage.getParentId());
		

		// when
		transaction.setAmount(new BigDecimal("202.54"));
		transaction.setParentId(102L);
		storage.save(transaction);
		
		// then
		Assert.assertFalse(storage.read(101L).isEmpty());
		Assert.assertFalse(storage.read(102L).isEmpty());

		
		// when 
		storage.remove(transaction);
		
		// then 
		List<Transaction> values=storage.read(transaction.getId());
		Assert.assertNotNull(values);
		Assert.assertTrue(values.isEmpty());
	}

	
	
	@Test
	public void readNonExistingValue(){
		// given
		ParentIdMemoryTransactionStorage storage=new ParentIdMemoryTransactionStorage();
		Long unknownId=new Random().nextLong();
		
		// when
		List<Transaction> valueFromStorage=storage.read(unknownId);
		
		// then
		Assert.assertNotNull(valueFromStorage);
		Assert.assertTrue(valueFromStorage.isEmpty());
	}
	

}
