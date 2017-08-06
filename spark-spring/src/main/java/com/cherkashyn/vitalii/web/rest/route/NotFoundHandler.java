package com.cherkashyn.vitalii.web.rest.route;

import com.cherkashyn.vitalii.web.rest.Constants;

import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.halt;

public class NotFoundHandler implements Route, Constants{

	@Override
	public Object handle(Request arg0, Response arg1) throws Exception {
		halt(Constants.RESULT_PAGE_NOT_FOUND);
		return null;
	}

}
