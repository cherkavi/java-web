package com.cherkashyn.vitalii.computer_shop.rediscount.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.computer_shop.rediscount.service.point.PointService;


@Component
@Path("/point")
public class PointRest {
	
	@Autowired(required=true)
	PointService service;
	
	@GET
	@Path("/trade")
	@Produces("application/json")
	public Response getTradePoints(){
		return RestUtils.buildPositiveResponse(service.getTradePoints());
	}

	@GET
	@Path("/name/{kod}")
	@Produces("application/json")
	public Response getTradePoints(@PathParam("kod") Integer kod){
		return RestUtils.buildPositiveResponse(service.getPointByKod(kod));
	}
	
}
