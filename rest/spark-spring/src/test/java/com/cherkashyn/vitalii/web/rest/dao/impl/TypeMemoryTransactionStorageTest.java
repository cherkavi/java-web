package com.cherkashyn.vitalii.web.rest.dao.impl;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.cherkashyn.vitalii.web.rest.model.Transaction;


public class TypeMemoryTransactionStorageTest {
	
	@Test
	public void checkCRUD(){
		// given
		TypeMemoryTransactionStorage storage=new TypeMemoryTransactionStorage();
		
		Transaction transaction=new Transaction();
		transaction.setId(100L);
		transaction.setParentId(101L);
		transaction.setType("test-type");
		transaction.setAmount(new BigDecimal("10.01"));
		
		// when
		storage.save(transaction);
		
		// then
		Transaction valueFromStorage=storage.read(transaction.getType()).iterator().next();
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
		Assert.assertFalse(storage.read(transaction.getType()).isEmpty());
		
		// when 
		storage.remove(transaction);
		
		// then 
		Assert.assertTrue(storage.read(transaction.getType()).isEmpty());
	}

	
	
	@Test
	public void readNonExistingValue(){
		// given
		TypeMemoryTransactionStorage storage=new TypeMemoryTransactionStorage();
		
		// when
		Collection<Transaction> valueFromStorage=storage.read("unknown-type");
		
		// then
		Assert.assertNotNull(valueFromStorage);
		Assert.assertTrue(valueFromStorage.isEmpty());
	}
	

}
