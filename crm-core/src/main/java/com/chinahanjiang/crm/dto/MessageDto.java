package com.chinahanjiang.crm.dto;

public class MessageDto {

	private boolean t;
	
	private String message;
	
	private int intF; //作为临时域-int
	
	private String StringF;//作为临时域-String
	
	public MessageDto(){
		
		this.t = true;
	}

	public MessageDto(boolean t, String message) {
		super();
		this.t = t;
		this.message = message;
	}

	public boolean isT() {
		return t;
	}

	public void setT(boolean t) {
		this.t = t;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getIntF() {
		return intF;
	}

	public void setIntF(int intF) {
		this.intF = intF;
	}

	public String getStringF() {
		return StringF;
	}

	public void setStringF(String stringF) {
		StringF = stringF;
	}
}
