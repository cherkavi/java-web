package com.cherkashyn.vitalii.web.rest.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherkashyn.vitalii.web.rest.dao.DocumentStorage;
import com.cherkashyn.vitalii.web.rest.model.Transaction;

@Repository
public class IdMemoryTransactionStorage implements DocumentStorage<Transaction, Long>{
	private Map<Long, Transaction> storage=new HashMap<Long, Transaction>();
	
	
	public void save(Transaction value) {
		storage.put(value.getId(), value);
	}

	public List<Transaction> read(Long id) {
		Transaction value=storage.get(id);
		if(value==null){
			return new ArrayList<>(0);
		}
		return Arrays.asList(value);
	}

	@Override
	public void remove(Transaction value){
		if(value==null){
			return;
		}
		storage.remove(value.getId());
	}


}
