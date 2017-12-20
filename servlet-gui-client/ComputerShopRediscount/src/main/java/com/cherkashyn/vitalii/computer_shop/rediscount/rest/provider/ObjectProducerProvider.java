package com.cherkashyn.vitalii.computer_shop.rediscount.rest.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
@Provider
@Produces("application/json") // Consumes
public class ObjectProducerProvider implements MessageBodyWriter<Object>{

	private ThreadLocal<String> threadLocal=new ThreadLocal<String>(); 
	
	@Override
	public long getSize(Object source, Class<?> arg1, Type arg2,
			Annotation[] arg3, MediaType arg4) {
		String returnValue=new Gson().toJson(source);
		threadLocal.set(returnValue);
		return returnValue.getBytes().length;
	}

	@Override
	public boolean isWriteable(Class<?> clazz, Type type, Annotation[] arg2,
			MediaType arg3) {
		return true; // for all objects
	}

	@Override
	public void writeTo(Object source, Class<?> arg1, Type arg2,
			Annotation[] arg3, MediaType arg4,
			MultivaluedMap<String, Object> arg5, OutputStream outputStream)
			throws IOException, WebApplicationException {
		outputStream.write(threadLocal.get().getBytes());
		outputStream.flush();
	}

}
