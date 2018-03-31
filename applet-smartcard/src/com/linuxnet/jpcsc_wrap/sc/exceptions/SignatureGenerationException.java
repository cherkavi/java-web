package com.linuxnet.jpcsc_wrap.sc.exceptions;

public class SignatureGenerationException extends GeneralSecurityException {
	public SignatureGenerationException() {
		super();
	}

	public SignatureGenerationException(String msg) {
		super(msg);
	}
}
