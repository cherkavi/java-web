package com.cherkashyn.vitalii.computer_shop.rediscount.service.point;

import com.cherkashyn.vitalii.computer_shop.rediscount.domain.Points;

public interface PointService {
	/**
	 * get Points 
	 * @return
	 */
	Points[] getTradePoints();

	Points getPointByKod(Integer kod);
	
}
