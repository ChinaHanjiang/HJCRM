package com.chinahanjiang.crm.dto;

public class ProductDto {

	private int id;
	
	private String name;
	
	private String code;
	
	private String shortCode;
	
	private int orders;
	
	private int productCatalogId;
	
	private String parentCatalog;
	
	private String productCatalog;
	
	private double standardPrice;
	
	private int productId;
	
	private int mixNum;
	
	private int userId;
	
	private String user;
	
	private String createTime;
	
	private String updateTime;
	
	private String remarks;

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

	public double getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(double standardPrice) {
		this.standardPrice = standardPrice;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public int getMixNum() {
		return mixNum;
	}

	public void setMixNum(int mixNum) {
		this.mixNum = mixNum;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public String getParentCatalog() {
		return parentCatalog;
	}

	public void setParentCatalog(String parentCatalog) {
		this.parentCatalog = parentCatalog;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	
}