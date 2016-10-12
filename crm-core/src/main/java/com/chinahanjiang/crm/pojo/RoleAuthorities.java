package com.chinahanjiang.crm.pojo;

import java.sql.Timestamp;

public class RoleAuthorities {

	private int id;
	
	private Role role;
	
	private Authorities authorities;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private int isDelete;

	public RoleAuthorities(){
		
		this.isDelete = 1;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Authorities getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Authorities authorities) {
		this.authorities = authorities;
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

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
