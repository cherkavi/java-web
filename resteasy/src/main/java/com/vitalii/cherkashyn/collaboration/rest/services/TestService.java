package com.vitalii.cherkashyn.collaboration.rest.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("message")
public class TestService {
	
	@GET
	@Path("/{echo}")
	public Response echoMessage(@PathParam("echo") String message){
		return Response.status(Status.OK).entity("this is REST response:"+message).build();
	}
}
