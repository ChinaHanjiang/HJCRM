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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "ProductQuote")
public class ProductQuote {

	private int id;

	private String code;
	
	private List<ProductQuoteDetails> productQuoteDetails;
	
	private Item item;
	
	private ItemAttachment itemAttactment;
	
	private double price;
	
	private int isDelete;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private User user;
	
	private String remarks;
	
	public ProductQuote(){
		
		this.isDelete = 0;
	}

	public ProductQuote(int id, String code,
			List<ProductQuoteDetails> productQuoteDetails, Item item,
			ItemAttachment itemAttactment, double price, int isDelete,
			Timestamp createTime, Timestamp updateTime, User user,
			String remarks) {
		super();
		this.id = id;
		this.code = code;
		this.productQuoteDetails = productQuoteDetails;
		this.item = item;
		this.itemAttactment = itemAttactment;
		this.price = price;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.user = user;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pq_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "pq_code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@OneToMany(targetEntity = ProductQuoteDetails.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "productQuote")
	@Fetch(FetchMode.SUBSELECT)
	public List<ProductQuoteDetails> getProductQuoteDetails() {
		return productQuoteDetails;
	}

	public void setProductQuoteDetails(List<ProductQuoteDetails> productQuoteDetails) {
		this.productQuoteDetails = productQuoteDetails;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pq_iid",referencedColumnName="i_id")
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pq_iaid",referencedColumnName="ia_id")
	public ItemAttachment getItemAttactment() {
		return itemAttactment;
	}

	public void setItemAttactment(ItemAttachment itemAttactment) {
		this.itemAttactment = itemAttactment;
	}

	@Column(name = "pq_price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "pq_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	@Column(name = "pq_createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "pq_updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pq_uid",referencedColumnName="u_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "pq_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
