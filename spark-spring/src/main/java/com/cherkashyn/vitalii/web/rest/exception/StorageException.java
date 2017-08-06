package com.cherkashyn.vitalii.web.rest.exception;

public class StorageException extends AppException{

	private static final long serialVersionUID = 1L;

	public StorageException() {
		super();
	}

	public StorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageException(String message) {
		super(message);
	}

	public StorageException(Throwable cause) {
		super(cause);
	}

}
