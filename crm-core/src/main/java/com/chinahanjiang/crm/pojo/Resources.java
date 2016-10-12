package com.chinahanjiang.crm.pojo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "resources")
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
	
	private Boolean enabled;
	
	private Boolean issys;
	
	private String module;
	
	private Set<AuthoritieResources> authoritieResources = new HashSet<AuthoritieResources>(0);
	
	public Resources(){
		
		this.isDelete = 1;
	}

	public Resources(Integer id, String name, String path, int priority,
			String type, int isDelete, Timestamp createTime,
			Timestamp updateTime, String remarks, Boolean enabled,
			Boolean issys, String module,
			Set<AuthoritieResources> authoritieResources) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.priority = priority;
		this.type = type;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remarks = remarks;
		this.enabled = enabled;
		this.issys = issys;
		this.module = module;
		this.authoritieResources = authoritieResources;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rs_id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "rs_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "rs_path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "rs_type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "rs_isdelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "rs_createtime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "rs_updatetime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "rs_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@OneToMany(targetEntity = AuthoritieResources.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "resources")
	@Fetch(FetchMode.SUBSELECT)
	public Set<AuthoritieResources> getAuthoritieResources() {
		return authoritieResources;
	}

	public void setAuthoritieResources(Set<AuthoritieResources> authoritieResources) {
		this.authoritieResources = authoritieResources;
	}

	@Column(name = "rs_priority")
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Column(name = "rs_enabled")
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "rs_issys")
	public Boolean getIssys() {
		return issys;
	}

	public void setIssys(Boolean issys) {
		this.issys = issys;
	}

	@Column(name = "rs_module")
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
}
