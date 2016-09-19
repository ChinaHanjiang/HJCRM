package com.chinahanjiang.crm.dto;

public class ProductQuoteDto {

	private int id;
	
	private int itemId;
	
	private String itemCode;
	
	private int pqId;
	
	private String pqCode;
	
	private Double price;
	
	private String remarks;

	private int status;
	
	private int itemFlag;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getItemFlag() {
		return itemFlag;
	}

	public void setItemFlag(int itemFlag) {
		this.itemFlag = itemFlag;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getPqId() {
		return pqId;
	}

	public void setPqId(int pqId) {
		this.pqId = pqId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getPqCode() {
		return pqCode;
	}

	public void setPqCode(String pqCode) {
		this.pqCode = pqCode;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
