package com.cherkashyn.vitalii.computer_shop.rediscount.client.output;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.cherkashyn.vitalii.computer_shop.rediscount.domain.Points;
import com.cherkashyn.vitalii.computer_shop.rediscount.domain.RediscountClientDto;

public class OutputHelper {
	public static String SERVER_URL="http://localhost:8080/ComputerShopRediscount";
	private final static int REDISCOUNT_NAME_LIMIT=40;
	
	private OutputHelper(){
	}
	
	public static void setServerUrl(String url){
		SERVER_URL="http://localhost:8080/ComputerShopRediscount";
	}
	
	public static void printLastRediscounted(RediscountClientDto[] elements, Writer out) throws IOException{
		if(!ArrayUtils.isEmpty(elements)){
			for(RediscountClientDto eachElement:elements){
				out.write("<tr>\n");
				out.write("<td>");
				out.write(eachElement.getNumber());
				out.write("</td>");
				out.write("<td>");
				out.write(StringUtils.substring(eachElement.getName(), 0, REDISCOUNT_NAME_LIMIT) );
				out.write("</td>");
				out.write("</tr>\n");
			}
		}
	}
	
	
	public static void printSelectOptions(Points[] points, Writer out, String pointIdParameter) throws IOException{
		if(points!=null && points.length>0){
			if(pointIdParameter==null){
				boolean isFirst=true;
				for(Points eachPoint: points){
					StringBuilder optionLine=new StringBuilder();
					optionLine.append("<option value=\"").append(eachPoint.getKod()+"\"");
					if(isFirst){
						isFirst=false;
						optionLine.append(" selected=\"selected\"");
					}
					optionLine.append(" >");
					optionLine.append(eachPoint.getName());
					optionLine.append("</option>");
					out.write(optionLine.toString());
				}
			}else{
				for(Points eachPoint: points){
					StringBuilder optionLine=new StringBuilder();
					optionLine.append("<option value=\"").append(eachPoint.getKod()+"\"");
					if( Integer.toString(eachPoint.getKod()).equals(pointIdParameter)){
						optionLine.append(" selected=\"selected\"");
					}
					optionLine.append(" >");
					optionLine.append(eachPoint.getName());
					optionLine.append("</option>");
					out.write(optionLine.toString());
				}
			}
			
		}
		
	}

}
