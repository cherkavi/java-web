package com.cherkashyn.vitalii.computer_shop.rediscount.service.rediscount;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

public class RediscountServiceImplTest {
	
	@Test
	public void testClearTime(){
		// given 
		RediscountServiceImpl service=new RediscountServiceImpl();
		Date date=new Date();
		
		// when 
		Date controlDate=service.clearTime(date);

		// then 
		Assert.assertEquals(0, DateUtils.getFragmentInMilliseconds(controlDate, Calendar.MILLISECOND));
		Assert.assertEquals(0, DateUtils.getFragmentInMilliseconds(controlDate, Calendar.SECOND));
		Assert.assertEquals(0, DateUtils.getFragmentInMilliseconds(controlDate, Calendar.MINUTE));
		Assert.assertEquals(0, DateUtils.getFragmentInMilliseconds(controlDate, Calendar.HOUR_OF_DAY));
		Assert.assertEquals(getFragment(date, Calendar.DAY_OF_MONTH), getFragment(controlDate, Calendar.DAY_OF_MONTH));
	}
	
	private int getFragment(Date date, int calendarField){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(calendarField);
	}
	
}
