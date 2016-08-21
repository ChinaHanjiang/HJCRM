package com.chinahanjiang.crm.dto;

public class LocationDto {

	private int id;
	
	private String name;
	
	private String code;
	
	private int parentId;
	
	private String fArea;
	
	private String fCode;
	
	private String sArea;
	
	private String sCode;
	
	private String tArea;
	
	private String tCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getfArea() {
		return fArea;
	}

	public void setfArea(String fArea) {
		this.fArea = fArea;
	}

	public String getsArea() {
		return sArea;
	}

	public void setsArea(String sArea) {
		this.sArea = sArea;
	}

	public String gettArea() {
		return tArea;
	}

	public void settArea(String tArea) {
		this.tArea = tArea;
	}

	public String getfCode() {
		return fCode;
	}

	public void setfCode(String fCode) {
		this.fCode = fCode;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String gettCode() {
		return tCode;
	}

	public void settCode(String tCode) {
		this.tCode = tCode;
	}
}
