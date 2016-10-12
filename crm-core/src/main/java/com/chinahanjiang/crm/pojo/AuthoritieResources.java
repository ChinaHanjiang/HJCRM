package com.chinahanjiang.crm.pojo;

import java.sql.Timestamp;

public class AuthoritieResources {

	private int id;
	
	private Authorities authorities;
	
	private Resources resources;
	
	private int isDelet;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	public AuthoritieResources() {
		
		this.isDelet = 1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Authorities getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Authorities authorities) {
		this.authorities = authorities;
	}

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	public int getIsDelet() {
		return isDelet;
	}

	public void setIsDelet(int isDelet) {
		this.isDelet = isDelet;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}