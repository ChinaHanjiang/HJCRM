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
@Table(name = "authorities")
public class Authorities {

	private Integer id;
	
	private String name;
	
	private String remarks;
	
	private int isDelete;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private Set<RoleAuthorities> roleAuthorities = new HashSet<RoleAuthorities>(0);
	
	private Set<AuthoritieResources> authoritieResources = new HashSet<AuthoritieResources>(); 
	
	private Boolean enabled;
	
	private Boolean issys;
	
	private String module;
	
	public Authorities(){
		
		this.isDelete = 1;
	}
	
	public Authorities(Integer id, String name, String remarks, int isDelete,
			Timestamp createTime, Timestamp updateTime,
			Set<RoleAuthorities> roleAuthorities,
			Set<AuthoritieResources> authoritieResources, Boolean enabled,
			Boolean issys, String module) {
		super();
		this.id = id;
		this.name = name;
		this.remarks = remarks;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.roleAuthorities = roleAuthorities;
		this.authoritieResources = authoritieResources;
		this.enabled = enabled;
		this.issys = issys;
		this.module = module;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "au_id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "au_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "au_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "au_isdelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "au_createtime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "au_updatetime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@OneToMany(targetEntity = RoleAuthorities.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "authorities")
	@Fetch(FetchMode.SUBSELECT)
	public Set<RoleAuthorities> getRoleAuthorities() {
		return roleAuthorities;
	}

	public void setRoleAuthorities(Set<RoleAuthorities> roleAuthorities) {
		this.roleAuthorities = roleAuthorities;
	}

	@OneToMany(targetEntity = AuthoritieResources.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "authorities")
	@Fetch(FetchMode.SUBSELECT)
	public Set<AuthoritieResources> getAuthoritieResources() {
		return authoritieResources;
	}

	public void setAuthoritieResources(Set<AuthoritieResources> authoritieResources) {
		this.authoritieResources = authoritieResources;
	}

	@Column(name = "au_enabled")
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "au_issys")
	public Boolean getIssys() {
		return issys;
	}

	public void setIssys(Boolean issys) {
		this.issys = issys;
	}

	@Column(name = "au_module")
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
}
