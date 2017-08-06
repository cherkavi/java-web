package com.cherkashyn.vitalii.web.rest.exception;

public class SerializeException extends AppException{

	private static final long serialVersionUID = 1L;

	public SerializeException() {
		super();
	}

	public SerializeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SerializeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerializeException(String message) {
		super(message);
	}

	public SerializeException(Throwable cause) {
		super(cause);
	}

	
}
