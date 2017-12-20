package com.cherkashyn.vitalii.computer_shop.rediscount.rest;

import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.computer_shop.rediscount.rest.provider.DateParameter;
import com.cherkashyn.vitalii.computer_shop.rediscount.service.rediscount.RediscountService;
import com.google.common.base.Preconditions;

@Component
@Path("/rediscount")
public class RediscountRest {

	@Autowired(required=true)
	RediscountService service;
	
	
	@GET
	@Produces("application/json")
	@Path("/last/{pointNumber}/{date}")
	public Response getLastRediscounted(@PathParam("pointNumber") int pointNumber, @PathParam("date") @DateParameter("dd.MM.yyyy") Date date){
		Preconditions.checkArgument(pointNumber>0, "need to set point number ");
		Preconditions.checkArgument(date!=null, "need to set point number ");
		return RestUtils.buildPositiveResponse(service.getLastRediscounted(date, pointNumber));
	}

	@GET
	@Produces("application/json")
	@Path("/check/{pointNumber}/{date}")
	public Response isRediscountPresent(@PathParam("pointNumber") int pointNumber, @PathParam("date") @DateParameter("dd.MM.yyyy") Date date){
		Preconditions.checkArgument(pointNumber>0, "need to set point number ");
		Preconditions.checkArgument(date!=null, "need to set point number ");
		return RestUtils.buildPositiveResponse(service.isRediscountPresent(date, pointNumber));
	}

	
	@POST
	@Path("/add")
	@Produces("application/json")
	public Response addRediscountElement(@FormParam("pointNumber") String rawPointNumber, @FormParam("date") @DateParameter("dd.MM.yyyy") Date date, @FormParam("serialNumber") String serialNumber){
		Preconditions.checkArgument(StringUtils.trimToNull(rawPointNumber)!=null, "need to set point number" );
		Integer pointNumber=Integer.parseInt(rawPointNumber);
		Preconditions.checkArgument(pointNumber>0, "need to set point number");
		Preconditions.checkArgument(date!=null, "need to set date");
		String clearSerialNumber=StringUtils.trimToNull(serialNumber);
		Preconditions.checkArgument(clearSerialNumber!=null, "check serialNumber");
		try{
			return RestUtils.buildPositiveResponse(service.putRediscount(date, pointNumber, clearSerialNumber));
		}catch(RuntimeException re){
			return RestUtils.buildErrorResponse(re.getMessage());
		}
		
	}
	
}
