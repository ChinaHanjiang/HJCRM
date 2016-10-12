package com.chinahanjiang.crm.pojo;

import java.sql.Timestamp;
import java.util.List;

public class Role {

	private Integer id;
	
	private String name;
	
	private String remarks;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private int isDelete; /*0-删除,1-没删除*/
	
	private List<UserRoles> userRoles;
	
	private List<RoleAuthorities> roleAuthorities;

	public Role(){
		
		this.isDelete = 1;
	}
	
	public Role(Integer id, String name, String remarks, Timestamp createTime,
			Timestamp updateTime, int isDelete,List<UserRoles> userRoles,
			List<RoleAuthorities> roleAuthorities) {
		super();
		this.id = id;
		this.name = name;
		this.remarks = remarks;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.isDelete = isDelete;
		this.userRoles = userRoles;
		this.roleAuthorities = roleAuthorities;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public List<UserRoles> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}

	public List<RoleAuthorities> getRoleAuthorities() {
		return roleAuthorities;
	}

	public void setRoleAuthorities(List<RoleAuthorities> roleAuthorities) {
		this.roleAuthorities = roleAuthorities;
	}
}
