package com.chinahanjiang.crm.dto;

public class ProductConfigurationDto {

	private int id;
	
	private int fpid;
	
	private String fproduct;
	
	private int spid;
	
	private String sproduct;
	
	private String code;
	
	private int productCatalogId;
	
	private String productCatalog;
	
	private int quantity;
	
	private String createTime;
	
	private String updateTime;
	
	private String remarks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFpid() {
		return fpid;
	}

	public void setFpid(int fpid) {
		this.fpid = fpid;
	}

	public String getFproduct() {
		return fproduct;
	}

	public void setFproduct(String fproduct) {
		this.fproduct = fproduct;
	}

	public int getSpid() {
		return spid;
	}

	public void setSpid(int spid) {
		this.spid = spid;
	}

	public String getSproduct() {
		return sproduct;
	}

	public void setSproduct(String sproduct) {
		this.sproduct = sproduct;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getProductCatalogId() {
		return productCatalogId;
	}

	public void setProductCatalogId(int productCatalogId) {
		this.productCatalogId = productCatalogId;
	}

	public String getProductCatalog() {
		return productCatalog;
	}

	public void setProductCatalog(String productCatalog) {
		this.productCatalog = productCatalog;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
