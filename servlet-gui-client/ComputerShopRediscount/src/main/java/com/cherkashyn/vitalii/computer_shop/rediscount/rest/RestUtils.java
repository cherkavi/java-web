package com.cherkashyn.vitalii.computer_shop.rediscount.rest;

import javax.ws.rs.core.Response;

public class RestUtils {

	public static Response buildPositiveResponse(Object entity){
		return Response.status(Response.Status.OK).entity(entity).build();
	}

	public static Response buildCreatedResponse(Object entity){
		return Response.status(Response.Status.CREATED).entity(entity).build();
	}

	public static Response buildErrorResponse(String message) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
	}

}
