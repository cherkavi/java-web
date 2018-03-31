package com.cherkashyn.vitalii.computer_shop.rediscount.client.restwrapper;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cherkashyn.vitalii.computer_shop.rediscount.domain.RediscountClientDto;
import com.google.gson.Gson;

public class RediscountRestWrapper {
	// /check/{pointNumber}/{date}
	private final static String REQUEST_SUFFIX_CHECK = "/rediscount/check/{0}/{1}";
	private final static String REQUEST_SUFFIX_LAST = "/rediscount/last/{0}/{1}";
	private final static String REQUEST_SUFFIX_ADD = "/rediscount/add";
	private final static String REQUEST_PARAMETER_POINT="pointNumber";
	private final static String REQUEST_PARAMETER_DATE="date";
	private final static String REQUEST_PARAMETER_SERIAL="serialNumber";

	public boolean requestIsRediscountPresent(String url, String pointNumber, String date) throws IOException {
		String rawValue = RestUtils.get(url + MessageFormat.format(REQUEST_SUFFIX_CHECK, pointNumber, date));
		Boolean returnValue=new Gson().fromJson(rawValue, Boolean.class);
		return returnValue;
	}
	
	public RediscountClientDto[] getLastRediscounted(String url, String pointNumber, String date) throws IOException {
		String rawValue = RestUtils.get(url + MessageFormat.format(REQUEST_SUFFIX_LAST, pointNumber, date));
		RediscountClientDto[] returnValue=new Gson().fromJson(rawValue, RediscountClientDto[].class);
		return returnValue;
	}
	

	public String addRediscountElement(String url, String pointNumber, String date, String serial) throws IOException {
		Map<String, String> parameters=new HashMap<String, String>();
		parameters.put(REQUEST_PARAMETER_POINT, pointNumber);
		parameters.put(REQUEST_PARAMETER_DATE, date);
		parameters.put(REQUEST_PARAMETER_SERIAL, StringUtils.removeStart(serial, "0"));
		
		String rawValue=RestUtils.post(url + REQUEST_SUFFIX_ADD, parameters);
		return new Gson().fromJson(rawValue, String.class);
	}
	

}
