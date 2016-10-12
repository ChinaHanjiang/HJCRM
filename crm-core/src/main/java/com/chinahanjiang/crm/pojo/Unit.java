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
@Table(name = "Unit")
public class Unit {

	private int id;
	
	private String name;
	
	private String ename;
	
	private Timestamp creteTime;
	
	private Timestamp updateTime;
	
	private User user;
	
	private int isDelete;
	
	private String remarks;
	
	public Unit(){
		
		this.isDelete = 1;
	}

	public Unit(int id, String name, String ename, Timestamp creteTime,
			Timestamp updateTime, User user, int isDelete, String remarks) {
		super();
		this.id = id;
		this.name = name;
		this.ename = ename;
		this.creteTime = creteTime;
		this.updateTime = updateTime;
		this.user = user;
		this.isDelete = isDelete;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ut_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "ut_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ut_ename")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Column(name = "ut_createTime")
	public Timestamp getCreteTime() {
		return creteTime;
	}

	public void setCreteTime(Timestamp creteTime) {
		this.creteTime = creteTime;
	}

	@Column(name = "ut_updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ut_uid",referencedColumnName="u_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "ut_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "ut_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
