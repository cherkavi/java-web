package com.linuxnet.jpcsc_wrap.util;


public class TimeUtils {

	static public void sleep(int nofMilliSeconds) {
		try {
			Thread.sleep(nofMilliSeconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}