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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 定义客户 2016-8-15
 * @author tree
 */
@Entity
@Table(name = "customer")
public class Customer {

	private int id;
	
	private String name;
	
	private String code;
	
	private Location location;
	
	private List<Contact> contacts;
	
	private String address;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private String telephone;
	
	private String fax;
	
	private int isDelete; /*0-删除,1-不删*/
	
	private String remarks;
	
	private Groups groups;
	
	private User user;

	public Customer(){
		
		this.isDelete = 1;
	}
	
	public Customer(int id, String name, String code, Groups groups, Location location,
			List<Contact> contacts, String address, Timestamp createTime,
			Timestamp updateTime, String telephone, String fax, int isDelete, String remarks) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.groups = groups;
		this.location = location;
		this.contacts = contacts;
		this.address = address;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.telephone = telephone;
		this.fax = fax;
		this.isDelete = isDelete;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "c_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="c_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="c_code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "g_uid",referencedColumnName="g_id")
	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	@ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "c_lid",referencedColumnName="l_id")
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@OneToMany(targetEntity = Contact.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "customer")
	@Fetch(FetchMode.SUBSELECT)
	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Column(name="c_address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="c_createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name="c_updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name="c_telephone")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name="c_fax")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name="c_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name="c_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "c_uid",referencedColumnName="u_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
