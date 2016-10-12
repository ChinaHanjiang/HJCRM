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
@Table(name = "role")
public class Role {

	private Integer id;
	
	private String name;
	
	private String remarks;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private int isDelete; /*0-删除,1-没删除*/
	
	private boolean enabled;
	
	private Boolean issys;
	
	//平台中的子系统
	private String module;
	
	private Set<UserRoles> userRoles = new HashSet<UserRoles>(0);
	
	private Set<RoleAuthorities> roleAuthorities = new HashSet<RoleAuthorities>(0);

	public Role(){
		
		this.isDelete = 1;
	}

	public Role(Integer id, String name, String remarks, Timestamp createTime,
			Timestamp updateTime, int isDelete, boolean enabled, Boolean issys,
			String module, Set<UserRoles> userRoles,
			Set<RoleAuthorities> roleAuthorities) {
		super();
		this.id = id;
		this.name = name;
		this.remarks = remarks;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.isDelete = isDelete;
		this.enabled = enabled;
		this.issys = issys;
		this.module = module;
		this.userRoles = userRoles;
		this.roleAuthorities = roleAuthorities;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rl_id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "rl_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "rl_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "rl_createtime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "rl_updatetime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "rl_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@OneToMany(targetEntity = UserRoles.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "role")
	@Fetch(FetchMode.SUBSELECT)
	public Set<UserRoles> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(targetEntity = RoleAuthorities.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "role")
	@Fetch(FetchMode.SUBSELECT)
	public Set<RoleAuthorities> getRoleAuthorities() {
		return roleAuthorities;
	}

	public void setRoleAuthorities(Set<RoleAuthorities> roleAuthorities) {
		this.roleAuthorities = roleAuthorities;
	}

	@Column(name = "rl_enabled")
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "rl_issys")
	public Boolean getIssys() {
		return issys;
	}

	public void setIssys(Boolean issys) {
		this.issys = issys;
	}

	@Column(name = "rl_module")
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
}
