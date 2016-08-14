package com.cherkashyn.vitalii.web.rest.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.web.rest.dao.impl.IdMemoryTransactionStorage;
import com.cherkashyn.vitalii.web.rest.dao.impl.ParentIdMemoryTransactionStorage;
import com.cherkashyn.vitalii.web.rest.dao.impl.TypeMemoryTransactionStorage;
import com.cherkashyn.vitalii.web.rest.model.Transaction;
import com.cherkashyn.vitalii.web.rest.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	IdMemoryTransactionStorage idStorage;
	
	@Autowired
	TypeMemoryTransactionStorage typeStorage;

	@Autowired
	ParentIdMemoryTransactionStorage parentStorage;
	
	@Override
	public Transaction save(Transaction value) {
		if(!isNew(value)){
			idStorage.remove(value);
			typeStorage.remove(value);
			parentStorage.remove(value);
		}
		idStorage.save(value);
		typeStorage.save(value);
		parentStorage.save(value);

		return value;
	}

	private boolean isNew(Transaction value) {
		return this.idStorage.read(value.getId()).isEmpty();
	}

	@Override
	public Transaction get(Long id) {
		List<Transaction> returnValue=idStorage.read(id);
		if(returnValue==null || returnValue.isEmpty()){
			return null;
		}
		return returnValue.get(0);
	}
	

	@Override
	public Collection<Transaction> getByType(String type) {
		Collection<Transaction> returnValue=typeStorage.read(type);
		if(returnValue!=null){
			return returnValue;
		}
		return new ArrayList<>(0);
	}

	@Override
	public BigDecimal sumByParent(Long parentId) {
		List<Transaction> values=parentStorage.read(parentId);
		if(values==null || values.isEmpty()){
			return BigDecimal.ZERO;
		}
		// TODO ask questin - do we need to add to amount the original (parent) transaction ??? 
		return values.stream()
				.map(eachTransaction->eachTransaction.getAmount())
				.reduce(BigDecimal.ZERO,(a,b)->a.add(b));
	}

}
