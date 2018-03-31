package com.linuxnet.jpcsc_wrap.util;

import java.math.BigInteger;

import com.linuxnet.jpcsc_wrap.sc.exceptions.UnsupportedEncodingException;

import com.linuxnet.jpcsc.Apdu;

public class TextUtils {

	public final static String hexChars[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	public static byte stringCharToBCDByte(String data, int pos) {
		return (byte) (Integer.parseInt(data.substring(pos, pos + 2), 16));
	}

	@SuppressWarnings("unused")
	private final static String nullString = "null";

	/** получить в виде HEX текста */
	public static String hexDump(BigInteger big) {
		return hexDump(big.toByteArray());
	}
	/** получить в виде HEX текста */
	public static String hexDump(byte[] data) {
		return hexDump(data, 0, data.length);
	}
	/** получить в виде HEX текста */
	public static String hexDump(byte[] data, int length) {
		return hexDump(data, 0, length);
	}
	/** получить в виде HEX текста */
	public static String hexDump(byte[] data, int offset, int length) {
		String result = "";
		String part = "";
		for (int i = 0; i < MathUtils.min(data.length, length); i++) {
			part = ""
					+ hexChars[(byte) (MathUtils.unsignedInt(data[offset + i]) / 16)]
					+ hexChars[(byte) (MathUtils.unsignedInt(data[offset + i]) % 16)];
			result = result + part;
		}
		return result;
	}

	static void print(byte[] response) {
		print(response, 0, response.length);
	}

	static void printInterpreted(byte[] response) {
		print(response, 0, response.length - 2);
		System.err.println("Status words  "
				+ Apdu.ba2s(response, response.length - 2, 2));
	}

	static void printNonInterpreted(String prefix, byte[] response,
			String postfix) {
		System.err.println(prefix + Apdu.ba2s(response, 0, response.length)
				+ postfix);
	}

	static void printInterpreted(String prefix, byte[] response, String postfix) {
		System.err.println(prefix + Apdu.ba2s(response, 0, response.length - 2)
				+ postfix);
		System.err.println("Status words  "
				+ Apdu.ba2s(response, response.length - 2, 2));
	}

	static void print(byte[] response, int off, int length) {
		System.err.println("Response  " + Apdu.ba2s(response, off, length));
	}

	final static public int tagLen = 1;

	public static int getTagIdentifier(byte[] data, int pos) {
		return data[pos];
	}

	public static int getDataLen(byte[] data, int pos) {
		int i = 0;
		int len = 0;
		while (MathUtils.unsignedInt(data[pos + i]) == 255) {
			len = len * 255 + 255;
			i++;
		}
		len += MathUtils.unsignedInt(data[pos + i]);
		return len;
	}

	public static int getLenLen(byte[] data, int pos) {
		int i = 0;
		while (MathUtils.unsignedInt(data[pos + i]) == 255) {
			i++;
		}
		return i + 1;
	}

	public static String getString(byte[] data, int pos, int len)
			throws UnsupportedEncodingException {
		return new String(data, pos, len);
	}

	public static byte[] selectBytes(byte[] data, int pos, int len)
			throws UnsupportedEncodingException {
		byte[] res = new byte[len];
		for (int i = 0; i < len; i++)
			res[i] = data[pos + i];
		return res;
	}
	// -- Вспомогательные методы
	// формирует строку hex определенной длины, добавлят слева нулями
	public static String intToHexStringF(int data, int len) {
		String Indata = Integer.toHexString(data).toUpperCase();
		String Outdata = "";
		for (int i = 0; i < (len - Indata.length()); i++) {
			Outdata = Outdata + "0";
		}
		Outdata = Outdata + Indata;
		return Outdata;
	};

}