package com.cherkashyn.vitalii.computer_shop.rediscount.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.cherkashyn.vitalii.computer_shop.rediscount.context.SpringApplicationContext;

@Path("/customer")
public class Test {

	@GET
	@Path("/print")
	public Response printMessage() {

		// customerBo = (CustomerBo) SpringApplicationContext.getBean("customerBo");
		
		return Response.status(200).build();

	}

}