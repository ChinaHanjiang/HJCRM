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

/**
 * 具体事件  2016-8-15
 * @author tree
 *
 */
@Entity
@Table(name="item")
public class Item {
	
	private int id;
	
	private String name;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private User user;
	
	private Task task;
	
	private Customer customer;
	
	//联系人
	private Contact contact;
	
	private int isDelete;
	
	private int status;/*0-进行中，1-完成*/
	
	private String remarks;
	
	public Item(){
		
		this.isDelete = 1;
	}
	
	public Item(int id, String name, Timestamp createTime,
			Timestamp updateTime, User user, Task task, Customer customer,
			Contact contact, int isDelete,
			int status, String remarks) {
		super();
		this.id = id;
		this.name = name;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.user = user;
		this.task = task;
		this.customer = customer;
		this.contact = contact;
		this.isDelete = isDelete;
		this.status = status;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "i_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="i_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="i_createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name="i_updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "i_uid",referencedColumnName="u_id")
	public User getUser() {
		return user;
	}

	
	public void setUser(User user) {
		this.user = user;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "i_cid",referencedColumnName="c_id")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "i_tid",referencedColumnName="t_id")
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "i_ctid",referencedColumnName="ct_id")
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@Column(name="i_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name="i_status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name="i_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
