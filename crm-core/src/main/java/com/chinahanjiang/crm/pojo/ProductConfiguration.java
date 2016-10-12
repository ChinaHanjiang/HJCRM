package com.chinahanjiang.crm.pojo;

import java.sql.Timestamp;

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
@Table(name = "ProductConfiguration")
public class ProductConfiguration {

	private Integer id;
	
	private Integer orders;
	
	private Product fproduct;
	
	private Product sproduct;
	
	private Integer quantity;
	
	private Integer isDelete;
	
	private String remarks;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	public ProductConfiguration(){
		
		this.isDelete = 1;
		this.quantity = 1;
	}

	public ProductConfiguration(Integer id, Product fproduct, Product sproduct,Integer quantity,
			Integer isDelete, String remarks, Timestamp createTime,Integer orders,
			Timestamp updateTime) {
		super();
		this.id = id;
		this.fproduct = fproduct;
		this.sproduct = sproduct;
		this.quantity = quantity;
		this.isDelete = isDelete;
		this.remarks = remarks;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.orders = orders;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pcfg_id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pcfg_fpid",referencedColumnName="p_id")
	public Product getFproduct() {
		return fproduct;
	}

	public void setFproduct(Product fproduct) {
		this.fproduct = fproduct;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pcfg_spid",referencedColumnName="p_id")
	public Product getSproduct() {
		return sproduct;
	}

	public void setSproduct(Product sproduct) {
		this.sproduct = sproduct;
	}
	
	@Column(name="pcfg_quantity")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "pcfg_isDelete")
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "pcfg_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "pcfg_createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "pcfg_updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name = "pcfg_order" , nullable = true)
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sproduct == null) ? 0 : sproduct.hashCode());
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
		ProductConfiguration other = (ProductConfiguration) obj;
		if (sproduct == null) {
			if (other.sproduct != null)
				return false;
		} else if (!sproduct.equals(other.sproduct))
			return false;
		return true;
	}
	
}