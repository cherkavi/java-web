package com.cherkashyn.vitalii.computer_shop.rediscount.client.restwrapper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.ArrayUtils;

public class RestUtils {
	private final static String GET="GET";
	private final static int[] GET_RESPONSES=new int[]{HttpURLConnection.HTTP_OK};
	private final static String POST="POST";
	private final static int[] POST_RESPONSES=new int[]{HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_CREATED};
	private final static String CONTENT_TYPE="application/json";
	
	
	public static String get(String urlLink) throws IOException, RuntimeException{
		URL url = new URL(urlLink);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(GET);
		// conn.setRequestProperty("Content-Type", CONTENT_TYPE);
		if (!ArrayUtils.contains(GET_RESPONSES, conn.getResponseCode())) {
			System.err.println("Can't connect to : "+ urlLink +" ("+conn.getResponseCode()+")");
			return null;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		StringBuilder returnValue = new StringBuilder();
		String output;
		while ((output = br.readLine()) != null) {
			returnValue.append(output);
		}
		conn.disconnect();
		return returnValue.toString();
	}

	
	private final static String DELIMITER="&";
	private final static String EQUALS="=";
	
	private static String parameterToString(String name, String value){
		StringBuilder returnValue=new StringBuilder();
		returnValue.append(name);
		returnValue.append(EQUALS);
		returnValue.append(value);
		return returnValue.toString();
	}
	
	private static String parametersToString(Map<String, String> parameters){
		StringBuilder returnValue=new StringBuilder();
		if(parameters!=null && !parameters.isEmpty()){
			for(Entry<String, String> eachEntry:parameters.entrySet()){
				if(returnValue.length()>0){
					returnValue.append(DELIMITER);
				}
				returnValue.append(parameterToString(eachEntry.getKey(), eachEntry.getValue()));
			}
		}
		return returnValue.toString();
	}

	public static String post(String urlLink, Map<String, String> parameters) throws IOException{
		URL url = new URL(urlLink);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(POST);
		conn.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED);
		
		conn.getOutputStream().write(parametersToString(parameters).getBytes());
		conn.getOutputStream().flush();
		if (!ArrayUtils.contains(POST_RESPONSES, conn.getResponseCode())) {
			System.err.println("Can't connect to : "+ urlLink +" ("+conn.getResponseCode()+")");
			throw new IOException(readAnswer(conn.getInputStream()));
			
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		StringBuilder returnValue = new StringBuilder();
		String output;
		while ((output = br.readLine()) != null) {
			returnValue.append(output);
		}
		conn.disconnect();
		return returnValue.toString();
	}

	private final static String LINE_SEPARATOR=System.getProperty("line.separator");
	
	private static String readAnswer(InputStream inputStream) throws IOException {
		BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder returnValue=new StringBuilder();
		String eachLine=null;
		while( (eachLine=reader.readLine())!=null ){
			returnValue.append(eachLine);
			returnValue.append(LINE_SEPARATOR);
		}
		return returnValue.toString();
	}
}
