package com.cherkashyn.vitalii.web.rest.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherkashyn.vitalii.web.rest.dao.DocumentStorage;
import com.cherkashyn.vitalii.web.rest.model.Transaction;

@Repository
public class ParentIdMemoryTransactionStorage implements DocumentStorage<Transaction, Long>{
	private Map<Long, List<Transaction>> storage=new HashMap<>();
	private final static List<Transaction> EMPTY_LIST=new ArrayList<>(0);
	
	
	public void save(Transaction value) {
		if(value.getParentId()==null){
			return;
		}
		List<Transaction> list=storage.get(value.getParentId());
		if(list!=null){
			list.add(value);
			return;
		}

		list=new LinkedList<>();
		list.add(value);
		storage.put(value.getParentId(), list);
	}

	public List<Transaction> read(Long id) {
		List<Transaction> returnValue=storage.get(id);
		return (returnValue==null)? EMPTY_LIST: returnValue;
	}

	@Override
	public void remove(Transaction value) {
		if(value==null){
			return;
		}
		main:for(Map.Entry<Long, List<Transaction>> eachEntry:storage.entrySet()){
			if(eachEntry.getValue()==null || eachEntry.getValue().isEmpty()){
				continue;
			}
			Iterator<Transaction> iterator=eachEntry.getValue().iterator();
			while(iterator.hasNext()){
				Transaction eachTransaction=iterator.next();
				if(value.equals(eachTransaction)){
					iterator.remove();
					break main;
				}
			}
		}
	}

}
