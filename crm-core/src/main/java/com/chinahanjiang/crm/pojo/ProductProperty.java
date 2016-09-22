package com.chinahanjiang.crm.pojo;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ProductProperty")
public class ProductProperty {

	private int id;
	
	private String name;
	
	private String value;
	
	private String ename;
	
	private String evalue;
	
	private int isDelete;
	
	private Product product;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private String remarks;
	
	private User user;
	
	public ProductProperty(){
		
		this.isDelete = 1;
	}

	public ProductProperty(int id, String name, String value, String ename,
			String evalue, int isDelete, Timestamp createTime,Product product,
			Timestamp updateTime, String remarks, User user) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.ename = ename;
		this.evalue = evalue;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remarks = remarks;
		this.product = product;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pp_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "pp_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pp_value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "pp_ename")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Column(name = "pp_evalue")
	public String getEvalue() {
		return evalue;
	}

	public void setEvalue(String evalue) {
		this.evalue = evalue;
	}

	@Column(name = "pp_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "pp_createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "pp_updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "pp_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "pp_pid",referencedColumnName="p_id")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pp_uid",referencedColumnName="u_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
