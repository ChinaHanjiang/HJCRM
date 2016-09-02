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
 * 具体事件  2016-8-15
 * @author tree
 *
 */
@Entity
@Table(name="item")
public class Item {
	
	private int id;
	
	private String name;
	
	private String code;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private User user;
	
	private ItemType itemType;
	
	private Task task;
	
	private Customer customer;
	
	//联系人
	private Contact contact;
	
	private int isDelete;
	
	private int status;/*0-进行中，1-完成*/
	
	private List<ItemAttachment> itemAttachements;
	
	private String remarks;
	
	public Item(){
		
		this.isDelete = 1;
	}
	
	public Item(int id, String name, String code, Timestamp createTime,
			Timestamp updateTime, User user, Task task, Customer customer,
			Contact contact, int isDelete, ItemType itemType,
			int status, List<ItemAttachment> itemAttachements, String remarks) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.user = user;
		this.task = task;
		this.customer = customer;
		this.contact = contact;
		this.isDelete = isDelete;
		this.itemType = itemType;
		this.status = status;
		this.itemAttachements = itemAttachements;
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
	
	@Column(name="i_code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	@OneToMany(targetEntity = ItemAttachment.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "item")
	@Fetch(FetchMode.SUBSELECT)
	public List<ItemAttachment> getItemAttachements() {
		return itemAttachements;
	}

	public void setItemAttachements(List<ItemAttachment> itemAttachements) {
		this.itemAttachements = itemAttachements;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "i_itid",referencedColumnName="it_id")
	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
}
