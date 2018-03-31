package com.linuxnet.jpcsc_wrap.sc.exceptions;

public class SignatureException extends GeneralSecurityException {
	public SignatureException() {
		super();
	}

	public SignatureException(String msg) {
		super(msg);
	}
}
