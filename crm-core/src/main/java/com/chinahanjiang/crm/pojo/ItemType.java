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

/**
 * 任务类型
 * @author tree
 *
 */
@Entity
@Table(name = "ItemType")
public class ItemType {

	private int id;
	
	private String name;
	
	private String code;
	
	private User user;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private int isDelete;
	
	private String remarks;
	
	public ItemType(){
		
		this.isDelete = 0;
	}

	public ItemType(int id, String name, String code, User user,
			Timestamp createTime, Timestamp updateTime, int isDelete,
			String remarks) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.user = user;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.isDelete = isDelete;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "it_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "it_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "it_code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "it_uid", referencedColumnName = "u_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "it_createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "it_updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "it_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "it_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
