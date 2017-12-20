package com.cherkashyn.vitalii.computer_shop.rediscount.rest.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Component
@Provider
public class ProviderException implements ExceptionMapper<Exception>{

	@Override
	public Response toResponse(Exception ex) {
		return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
	}

}
