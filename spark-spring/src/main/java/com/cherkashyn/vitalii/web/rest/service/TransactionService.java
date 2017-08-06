package com.cherkashyn.vitalii.web.rest.service;

import java.math.BigDecimal;
import java.util.Collection;

import com.cherkashyn.vitalii.web.rest.model.Transaction;

public interface TransactionService {
	/**
	 * save into storage 
	 * @param value
	 * @return
	 */
	Transaction save(Transaction value);
	
	/**
	 * get from storage 
	 * @param id
	 * @return
	 */
	Transaction get(Long id);
	
	/**
	 * get all by certain type 
	 * @param type
	 * @return
	 */
	Collection<Transaction> getByType(String type);
	
	/**
	 * get all transaction which have the same {@link Transaction#getParentId()}  
	 * @param id
	 * @return
	 */
	BigDecimal sumByParent(Long parentId);
	
}
