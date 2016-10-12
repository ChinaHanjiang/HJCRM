package com.chinahanjiang.crm.exception;

public class SystemRunException extends Exception {
	private static final long serialVersionUID = -4616830502974081779L;

	public SystemRunException() {
	}

	public SystemRunException(String message) {
		super(message);
	}

	public SystemRunException(Exception e) {
		super(e);
	}

	public SystemRunException(String message, Throwable cause) {
		super(message, cause);
	}
}
