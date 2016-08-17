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
import javax.persistence.Table;

/**
 * 客户联系人 2016-8-15
 * @author tree
 *
 */
@Entity
@Table(name = "contact")
public class Contact {

	private int id;
	
	private String name;
	
	private int sex;/*0-女,1-男*/
	
	private String mobilePhone;
	
	private String email;
	
	private String duty;
	
	private int isDelete;/*0-删除,1-不删*/
	
	private Customer customer;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private String remarks;
	
	public Contact(){
		
	}

	public Contact(int id, String name, int sex, String mobilePhone,
			String email, String duty, int isDelete, Customer customer,
			Timestamp createTime, Timestamp updateTime, String remarks) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.duty = duty;
		this.isDelete = isDelete;
		this.customer = customer;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ct_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="ct_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="ct_sex")
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Column(name="ct_mobilePhone")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name="ct_email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="ct_duty")
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Column(name="ct_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name="ct_createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name="ct_updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name="ct_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "ct_cid",referencedColumnName="c_id")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
