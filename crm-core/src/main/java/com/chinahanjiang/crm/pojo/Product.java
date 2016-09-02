package com.chinahanjiang.crm.pojo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 产品
 * @author tree
 *
 */
@Entity
@Table(name = "Product")
public class Product {

	private int id;
	
	private String name;
	
	private String code;
	
	private ProductCatalog productCatalog;
	
	private List<Product> productMix;
	
	private List<Product> beyondProducts;
	
	private List<ProductAndQuoteRelation> quoteProducts;
	
	private List<Task> tasks;
	
	private double standardPrice;
	
	private double definePrice;
	
	private int isDelete;
	
	private User user;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private String remarks;
	
	public Product(){
		 
		this.isDelete = 0;
	}

	public Product(int id, String name, String code, List<Product> productMix,
			List<Product> beyongProduct, ProductCatalog productCatalog, double standardPrice, 
			double definePrice, int isDelete, User user,Timestamp createTime,
			Timestamp updateTime, String remarks, List<ProductAndQuoteRelation> quoteProducts,
			List<Task> tasks) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.productCatalog = productCatalog;
		this.standardPrice = standardPrice;
		this.definePrice = definePrice;
		this.isDelete = isDelete;
		this.user = user;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remarks = remarks;
		this.quoteProducts = quoteProducts;
		this.tasks = tasks;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "p_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "p_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "p_code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "p_pcid",referencedColumnName="pc_id")
	public ProductCatalog getProductCatalog() {
		return productCatalog;
	}

	public void setProductCatalog(ProductCatalog productCatalog) {
		this.productCatalog = productCatalog;
	}

	@Column(name = "p_standardprice")
	public double getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(double standardPrice) {
		this.standardPrice = standardPrice;
	}

	@Column(name = "p_defineprice")
	public double getDefinePrice() {
		return definePrice;
	}

	public void setDefinePrice(double definePrice) {
		this.definePrice = definePrice;
	}

	@Column(name = "p_isdelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "p_uid",referencedColumnName="u_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "p_createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "p_updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "p_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "beyondProducts")
	public List<Product> getProductMix() {
		return productMix;
	}

	public void setProductMix(List<Product> productMix) {
		this.productMix = productMix;
	}

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "ProductConfigure",
	joinColumns = {@JoinColumn(name = "pcf_pid", referencedColumnName = "p_id")},
	inverseJoinColumns = {@JoinColumn(name = "pcf_pmid", referencedColumnName ="p_id")})
	public List<Product> getBeyondProducts() {
		return beyondProducts;
	}

	public void setBeyondProducts(List<Product> beyondProducts) {
		this.beyondProducts = beyondProducts;
	}

	@OneToMany(targetEntity = ProductAndQuoteRelation.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "product")
	@Fetch(FetchMode.SUBSELECT)
	public List<ProductAndQuoteRelation> getQuoteProducts() {
		return quoteProducts;
	}

	public void setQuoteProducts(List<ProductAndQuoteRelation> quoteProducts) {
		this.quoteProducts = quoteProducts;
	}

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "TaskProducts",
	joinColumns = {@JoinColumn(name = "tp_pid", referencedColumnName = "p_id")},
	inverseJoinColumns = {@JoinColumn(name = "tp_tid", referencedColumnName ="t_id")})
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
}
