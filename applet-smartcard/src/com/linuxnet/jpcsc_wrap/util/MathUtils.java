package com.linuxnet.jpcsc_wrap.util;

public class MathUtils {
	public static int unsignedInt(int a) {
		if (a < 0) {
			return a + 256;
		}
		return a;
	}

	public static int min(int a, int b) {
		if (a < b) {
			return a;
		}
		return b;
	}

	public static byte stringCharToBCDByte(String data, int pos) {
		return (byte) (Integer.parseInt(data.substring(pos, pos + 2), 16));
	}
}
