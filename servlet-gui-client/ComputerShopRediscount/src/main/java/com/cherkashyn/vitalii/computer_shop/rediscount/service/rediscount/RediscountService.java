package com.cherkashyn.vitalii.computer_shop.rediscount.service.rediscount;

import java.util.Date;

import com.cherkashyn.vitalii.computer_shop.rediscount.domain.RediscountClientDto;

public interface RediscountService {
	public static enum RequestResult{
		ALREADY_SCANNED(0),
		ADDED(1),
		NOT_FOUND_IN_SOURCE(2),
		BAD_NUMBER(3);

		private int code;
		RequestResult(int code) {
			this.code=code;
		}
		
		public String toString(){
			return Integer.toString(code);
		}
	}
	/**
	 * get last rediscounted commodity 
	 * @param date
	 * @param pointNumber
	 * @return
	 */
	RediscountClientDto[] getLastRediscounted(Date date, int pointNumber);

	/**
	 * check rediscount for present data 
	 * @param date
	 * @param pointNumber
	 * @return
	 */
	boolean isRediscountPresent(Date date, int pointNumber);

	/**
	 * try to put one certain element into rediscount
	 * @param date
	 * @param pointNumber
	 * @param serialNumber
	 * @return
	 */
	RequestResult putRediscount(Date date, int pointNumber, String serialNumber);
}
