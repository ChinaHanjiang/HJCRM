package com.chinahanjiang.crm.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ProductAndQuoteRelation")
public class ProductAndQuoteRelation {

	private int id;
	
	private Product product;
	
	private ProductQuoteDetails productQuoteDetails;
	
	private double defindPrice;
	
	private int isDelete;
	
	private String remarks;
	
	public ProductAndQuoteRelation(){
		
		isDelete = 0;
	}

	public ProductAndQuoteRelation(int id, Product product,
			ProductQuoteDetails productQuoteDetails, double defindPrice,
			int isDelete, String remarks) {
		super();
		this.id = id;
		this.product = product;
		this.productQuoteDetails = productQuoteDetails;
		this.defindPrice = defindPrice;
		this.isDelete = isDelete;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "paqr_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "paqr_pid",referencedColumnName="p_id")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "paqr_pqdid",referencedColumnName="pqd_id")
	public ProductQuoteDetails getProductQuoteDetails() {
		return productQuoteDetails;
	}

	public void setProductQuoteDetails(ProductQuoteDetails productQuoteDetails) {
		this.productQuoteDetails = productQuoteDetails;
	}

	@Column(name = "paqr_defindPrice")
	public double getDefindPrice() {
		return defindPrice;
	}

	public void setDefindPrice(double defindPrice) {
		this.defindPrice = defindPrice;
	}

	@Column(name = "paqr_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "paqr_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
