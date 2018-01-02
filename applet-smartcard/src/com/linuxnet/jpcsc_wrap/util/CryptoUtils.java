package com.linuxnet.jpcsc_wrap.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtils {
	public static byte[] computeSha1(byte[] data)
	throws NoSuchAlgorithmException {
	MessageDigest md = MessageDigest.getInstance("SHA1");
	md.update(data);
	return md.digest();
}
}
