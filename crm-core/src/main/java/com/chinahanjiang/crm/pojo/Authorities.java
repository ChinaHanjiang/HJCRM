package com.chinahanjiang.crm.pojo;

import java.sql.Timestamp;
import java.util.List;

public class Authorities {

	private Integer id;
	
	private String name;
	
	private String remarks;
	
	private int isDelete;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private List<RoleAuthorities> roleAuthorities;
	
	private List<AuthoritieResources> authoritieResources; 
	
	public Authorities(){
		
		this.isDelete = 1;
	}
	
	public Authorities(Integer id, String name, String remarks, int isDelete,
			Timestamp createTime, Timestamp updateTime,List<RoleAuthorities> roleAuthorities,
			List<AuthoritieResources> authoritieResources) {
		super();
		this.id = id;
		this.name = name;
		this.remarks = remarks;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.roleAuthorities = roleAuthorities;
		this.authoritieResources = authoritieResources;
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

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
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

	public List<RoleAuthorities> getRoleAuthorities() {
		return roleAuthorities;
	}

	public void setRoleAuthorities(List<RoleAuthorities> roleAuthorities) {
		this.roleAuthorities = roleAuthorities;
	}

	public List<AuthoritieResources> getAuthoritieResources() {
		return authoritieResources;
	}

	public void setAuthoritieResources(List<AuthoritieResources> authoritieResources) {
		this.authoritieResources = authoritieResources;
	}
	
}
