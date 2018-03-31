package com.cherkashyn.vitalii.computer_shop.rediscount.client.restwrapper;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

import com.cherkashyn.vitalii.computer_shop.rediscount.domain.Points;
import com.google.gson.Gson;

public class PointRestWrapper {
	
	private final static String REQUEST_SUFFIX_TRADE = "/point/trade";
	private final static String REQUEST_SUFFIX_POINT_NAME = "/point/name/{0}";

	public Points[] requestPoints(String url) throws IOException {
		String rawValue = RestUtils.get(url + REQUEST_SUFFIX_TRADE);
		Points[] returnValue=new Gson().fromJson(rawValue, Points[].class);
		return returnValue;
	}

	public String requestPointName(String url, String pointNumber) throws IOException {
		String rawValue = RestUtils.get(url + MessageFormat.format(REQUEST_SUFFIX_POINT_NAME, pointNumber));
		Points returnValue=new Gson().fromJson(rawValue, Points.class);
		if(returnValue!=null){
			return returnValue.getName();
		}else{
			return StringUtils.EMPTY;
		}
	}

}
