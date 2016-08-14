package com.cherkashyn.vitalii.web.rest.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.cherkashyn.vitalii.web.rest.dao.impl.IdMemoryTransactionStorage;
import com.cherkashyn.vitalii.web.rest.dao.impl.ParentIdMemoryTransactionStorage;
import com.cherkashyn.vitalii.web.rest.dao.impl.TypeMemoryTransactionStorage;
import com.cherkashyn.vitalii.web.rest.model.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {
	@Mock
	IdMemoryTransactionStorage idStorage;
	@Mock
	TypeMemoryTransactionStorage typeStorage;
	@Mock
	ParentIdMemoryTransactionStorage parentStorage;
	
	@InjectMocks
	TransactionServiceImpl service;
	
	@SuppressWarnings("unchecked")
	@Test
	public void checkSave(){
		// given
		Transaction transaction=new Transaction();
		transaction.setId(100L);
		transaction.setParentId(200L);
		transaction.setType("test");
		transaction.setAmount(new BigDecimal("20.1"));
		
		// when
		service.save(transaction);
		
		// then
		Mockito.verify(idStorage, Mockito.times(1)).save(transaction);
		Mockito.verify(typeStorage, Mockito.times(1)).save(transaction);
		Mockito.verify(parentStorage, Mockito.times(1)).save(transaction);
		
		Mockito.verify(idStorage, Mockito.never()).remove(transaction);
		Mockito.verify(typeStorage, Mockito.never()).remove(transaction);
		Mockito.verify(parentStorage, Mockito.never()).remove(transaction);

		Mockito.reset(idStorage, typeStorage, parentStorage);
		
		transaction.setType("new-type");
		Mockito.when(idStorage.read(transaction.getId())).thenReturn(Arrays.asList(transaction));
		
		// when
		service.save(transaction);
		Mockito.verify(idStorage, Mockito.times(1)).save(transaction);
		Mockito.verify(typeStorage, Mockito.times(1)).save(transaction);
		Mockito.verify(parentStorage, Mockito.times(1)).save(transaction);
		
		Mockito.verify(idStorage, Mockito.times(1)).remove(transaction);
		Mockito.verify(typeStorage, Mockito.times(1)).remove(transaction);
		Mockito.verify(parentStorage, Mockito.times(1)).remove(transaction);
	}
	
	@Test
	public void checkRead(){
		// given
		Transaction transaction=new Transaction();
		transaction.setId(100L);
		transaction.setParentId(200L);
		transaction.setType("test");
		transaction.setAmount(new BigDecimal("20.1"));
		List<Transaction> list=Arrays.asList(transaction);
		Mockito.when(idStorage.read(100L)).thenReturn(list);
		
		// when
		Transaction returnValue=service.get(transaction.getId());
		
		// then
		Assert.assertEquals(transaction, returnValue);
	}
	
	@Test
	public void checkEmptyRead(){
		// given
		Long controlId=100L;
		Mockito.when(idStorage.read(controlId)).thenReturn(null);
		
		// when
		Transaction returnValue=service.get(controlId);
		
		// then
		Assert.assertNull(returnValue);
	}
	
	@Test
	public void checkGetByType(){
		// given
		Transaction transaction=new Transaction();
		transaction.setId(100L);
		transaction.setParentId(200L);
		transaction.setType("test");
		transaction.setAmount(new BigDecimal("20.1"));
		List<Transaction> list=Arrays.asList(transaction);
		Mockito.when(typeStorage.read(transaction.getType())).thenReturn(list);
		
		// when
		Collection<Transaction> valueFromStorage=service.getByType(transaction.getType());
		
		// then
		Assert.assertSame(list, valueFromStorage);
	}
	
	@Test
	public void checkEmptyGetByType(){
		// given
		String transactionType="test-type";
		Mockito.when(typeStorage.read(transactionType)).thenReturn(null);
		
		// when
		Collection<Transaction> valueFromStorage=service.getByType(transactionType);
		
		// then
		Assert.assertNotNull(valueFromStorage);
		Assert.assertTrue(valueFromStorage.isEmpty());
	}
	
	@Test
	public void checkSumForEmpty(){
		// given
		Long parentId=101L;
		Mockito.when(parentStorage.read(parentId)).thenReturn(null);
		
		// when
		BigDecimal value=service.sumByParent(parentId);
		
		// then
		Assert.assertNotNull(value);
		Assert.assertEquals(BigDecimal.ZERO, value);
	}
	
	@Test
	public void checkSum(){
		// given
		Transaction transaction1=new Transaction();
		transaction1.setId(100L);
		transaction1.setParentId(200L);
		transaction1.setType("test");
		transaction1.setAmount(new BigDecimal("20.1"));
		
		Transaction transaction2=new Transaction();
		transaction2.setId(101L);
		transaction2.setParentId(200L);
		transaction2.setType("test");
		transaction2.setAmount(new BigDecimal("10.5"));
		
		Mockito.when(parentStorage.read(200L)).thenReturn(Arrays.asList(transaction1, transaction2));
		
		// when
		BigDecimal value=service.sumByParent(200L);
		
		// then
		Assert.assertNotNull(value);
		Assert.assertEquals(transaction1.getAmount().add(transaction2.getAmount()), value);
	}
}
