package com.cherkashyn.vitalii.web.rest.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.cherkashyn.vitalii.web.rest.dao.DocumentStorage;
import com.cherkashyn.vitalii.web.rest.model.Transaction;

@Repository
public class TypeMemoryTransactionStorage implements DocumentStorage<Transaction, String>{
	private Map<String, Set<Transaction>> storage=new HashMap<String, Set<Transaction>>();
	private final static Set<Transaction> EMPTY_LIST=new HashSet<>(0);
	
	public void save(Transaction value) {
		if(value==null){
			return;
		}
		if(value.getType()==null){
			return;
		}
		Set<Transaction> list=storage.get(value.getType());
		if(list!=null){
			list.add(value);
			return;
		}

		list=new HashSet<>();
		list.add(value);
		storage.put(value.getType(), list);
	}

	public Collection<Transaction> read(String type) {
		Collection<Transaction> returnValue=this.storage.get(type);
		return (returnValue==null)?EMPTY_LIST:returnValue;
	}

	@Override
	public void remove(Transaction value)  {
		if(value==null){
			return;
		}
		main:for(Map.Entry<String, Set<Transaction>> eachEntry:storage.entrySet()){
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
