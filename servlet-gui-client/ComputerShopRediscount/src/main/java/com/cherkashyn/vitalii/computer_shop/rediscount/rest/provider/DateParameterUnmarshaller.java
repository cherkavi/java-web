package com.cherkashyn.vitalii.computer_shop.rediscount.rest.provider;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.resteasy.spi.StringParameterUnmarshaller;
import org.jboss.resteasy.util.FindAnnotation;
import org.springframework.stereotype.Component;

@Component
public class DateParameterUnmarshaller implements StringParameterUnmarshaller<Date>{
	private SimpleDateFormat format;
	
	@Override
	public Date fromString(String stringDate) {
		try{
			return format.parse(stringDate);
		}catch(ParseException pe){
			throw new RuntimeException(MessageFormat.format("can't parse Date:{} when expected: {}", stringDate, format.getDateFormatSymbols().toString()), pe);
		}
	}

	@Override
	public void setAnnotations(Annotation[] annotations) {
		DateParameter format=FindAnnotation.findAnnotation(annotations, DateParameter.class);
		this.format=new SimpleDateFormat(format.value());
	}

}
