package com.chinahanjiang.crm.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 产品报价详细表
 * @author tree
 *
 */
@Entity
@Table(name = "ProductQuoteDetails")
public class ProductQuoteDetails {

	private int id;
	
	private ProductQuote productQuote;
	
	private Product product;
	
	private List<ProductAndQuoteRelation> products;
	
	private double price;
	
	private int isDelete;
	
	private String remarks;
	
	public ProductQuoteDetails(){
		
		this.isDelete = 1;
	}

	public ProductQuoteDetails(int id, ProductQuote productQuote,
			Product product, List<ProductAndQuoteRelation> products, double price,
			int isDelete, String remarks) {
		super();
		this.id = id;
		this.productQuote = productQuote;
		this.product = product;
		this.products = products;
		this.price = price;
		this.isDelete = isDelete;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pqd_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "pqd_pqid",referencedColumnName="pq_id")
	public ProductQuote getProductQuote() {
		return productQuote;
	}

	public void setProductQuote(ProductQuote productQuote) {
		this.productQuote = productQuote;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pqd_pid",referencedColumnName="p_id")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@OneToMany(targetEntity = ProductAndQuoteRelation.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "productQuoteDetails")
	@Fetch(FetchMode.SUBSELECT)
	public List<ProductAndQuoteRelation> getProducts() {
		return products;
	}

	public void setProducts(List<ProductAndQuoteRelation> products) {
		this.products = products;
	}

	@Column(name = "pqd_price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "pqd_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "pqd_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
