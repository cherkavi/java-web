package com.cherkashyn.vitalii.web.rest.transport;

import java.io.IOException;

import com.cherkashyn.vitalii.web.rest.exception.SerializeException;
import com.cherkashyn.vitalii.web.rest.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;

public class DomainSerializer {

	public static String render(Object value) throws SerializeException{
		try {
			return JsonConverter.toJson(value);
		} catch (JsonProcessingException e) {
			throw new SerializeException(e);
		}
	}

	public static Transaction unrender(String body, Class<Transaction> clazz) throws SerializeException {
		try {
			return JsonConverter.fromJson(body, clazz);
		} catch (IOException e) {
			throw new SerializeException(e);
		}
	}
	 
}
