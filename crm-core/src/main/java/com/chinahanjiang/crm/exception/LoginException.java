/**
 * Project:pereva
 * File:LoginException.java
 * Create Time:2010-4-6 下午03:49:32
 * Author:xiongw
 * 用�?说明�?
 *
 */
package com.chinahanjiang.crm.exception;

public class LoginException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginException() {}

	public LoginException(String s) {
		super(s);
	}

	public LoginException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public LoginException(Throwable throwable) {
		super(throwable);
	}
}