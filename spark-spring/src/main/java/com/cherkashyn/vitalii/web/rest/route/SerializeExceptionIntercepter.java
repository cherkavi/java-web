package com.cherkashyn.vitalii.web.rest.route;

import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.web.rest.Constants;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;


@Component
public class SerializeExceptionIntercepter implements ExceptionHandler, Constants{

	@Override
	public void handle(Exception exception, Request request, Response response) {
		response.status(RESULT_BAD_REQUEST);
		response.body("check body/parameters of your request");
	}


}
