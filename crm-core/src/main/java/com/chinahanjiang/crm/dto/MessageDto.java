package com.chinahanjiang.crm.dto;

public class MessageDto {

	private boolean t;
	
	private String message;
	
	public MessageDto(){
		
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
	
	
}
