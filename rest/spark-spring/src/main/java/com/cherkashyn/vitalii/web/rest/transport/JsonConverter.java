package com.cherkashyn.vitalii.web.rest.transport;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class JsonConverter {
	private JsonConverter(){
	}
	
	static String toJson(Object object) throws JsonProcessingException{
		return new ObjectMapper().writeValueAsString(object);
	}
	
	static <T> T fromJson(String value, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException{
		return new ObjectMapper().readValue(value.getBytes(), clazz);
	}
	
}
