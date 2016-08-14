package com.cherkashyn.vitalii.web.rest.dao;

import java.util.Collection;
import java.util.List;

import com.cherkashyn.vitalii.web.rest.exception.StorageException;

public interface DocumentStorage<Document, Key>{
	/**
	 * save into storage 
	 * @param value
	 * @return
	 */
	void save(Document value) throws StorageException;
	
	/**
	 * get from storage 
	 * @param id
	 * @return
	 */
	Collection<Document> read(Key id) throws StorageException;
	
	/**
	 * remove element from storage
	 * @param value
	 * @throws StorageException
	 */
	void remove(Document value) throws StorageException;

}
