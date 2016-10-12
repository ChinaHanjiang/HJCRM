package com.chinahanjiang.crm.pojo;

import java.sql.Timestamp;
import java.util.List;

public class Resources {

	private Integer id;
	
	private String name;
	
	private String path;
	
	private int priority;
	
	private String type;
	
	private int isDelete;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private String remarks;
	
	private List<AuthoritieResources> authoritieResources;
	
	public Resources(){
		
		this.isDelete = 1;
	}

	public Resources(Integer id, String name, String path, String type,
			int isDelete, Timestamp createTime, Timestamp updateTime,
			String remarks,List<AuthoritieResources> authoritieResources,
			int priority) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.type = type;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remarks = remarks;
		this.authoritieResources = authoritieResources;
		this.priority = priority;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<AuthoritieResources> getAuthoritieResources() {
		return authoritieResources;
	}

	public void setAuthoritieResources(List<AuthoritieResources> authoritieResources) {
		this.authoritieResources = authoritieResources;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
