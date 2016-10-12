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

	private Integer id;
	
	private String name;
	
	private String ename;
	
	private String shortCode;
	
	private String code;
	
	private Integer orders;
	
	private ProductCatalog productCatalog;
	
	private List<ProductConfiguration> productMix;
	
	private List<ProductConfiguration> beyondProducts;
	
	private List<ProductAndQuoteRelation> quoteProducts;
	
	private List<Task> tasks;
	
	private List<Item> items;
	
	private double standardPrice;
	
	private double definePrice;
	
	private Integer isDelete;
	
	private User user;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private String remarks;
	
	private Unit unit;
	
	private List<ProductProperty> properties;
	
	public Product(){
		 
		this.isDelete = 1;
	}

	public Product(Integer id, String name, String code, List<Product> productMix,
			List<Product> beyongProduct, ProductCatalog productCatalog, double standardPrice, 
			double definePrice, Integer isDelete, User user,Timestamp createTime,
			Timestamp updateTime, String remarks, List<ProductAndQuoteRelation> quoteProducts,
			List<Task> tasks, List<Item> items, Unit unit, List<ProductProperty> properties,
			String ename) {
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
		this.items = items;
		this.unit = unit;
		this.properties = properties;
		this.ename = ename;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "p_id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
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

	@OneToMany(targetEntity = ProductConfiguration.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "sproduct")
	@Fetch(FetchMode.SUBSELECT)
	public List<ProductConfiguration> getProductMix() {
		return productMix;
	}

	public void setProductMix(List<ProductConfiguration> productMix) {
		this.productMix = productMix;
	}

	@OneToMany(targetEntity = ProductConfiguration.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "fproduct")
	@Fetch(FetchMode.SUBSELECT)
	public List<ProductConfiguration> getBeyondProducts() {
		return beyondProducts;
	}

	public void setBeyondProducts(List<ProductConfiguration> beyondProducts) {
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

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Column(name = "p_shortcode")
	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	@Column(name = "p_oders" ,nullable=true)
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "p_utid",referencedColumnName="ut_id")
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@OneToMany(targetEntity = ProductProperty.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "product")
	@Fetch(FetchMode.SUBSELECT)
	public List<ProductProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<ProductProperty> properties) {
		this.properties = properties;
	}

	@Column(name = "p_ename" , nullable=true)
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
