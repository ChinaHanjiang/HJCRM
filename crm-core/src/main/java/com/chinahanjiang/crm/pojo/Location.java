package com.chinahanjiang.crm.pojo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 地址 2016-8-15
 * @author tree
 *
 */
@Entity
@Table(name = "location")
public class Location {

	private int id;
	
	private String name;
	
	private String code;//区域编码
	
	private int order;
	
	private Location parentLoc;
	
	private String state;
	
	private List<Location> childLocs;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private String remarks;
	
	private int isDelete; /*0-删除,1-不删*/
	
	private User user;
	
	public Location(){
		
		this.isDelete = 1;
	}
	public Location(int id, String name, String code, int order, Location parentLoc,
			String state, List<Location> childLocs, Timestamp createTime,
			Timestamp updateTime, String remarks, int isDelete, User user) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.order = order;
		this.parentLoc = parentLoc;
		this.state = state;
		this.childLocs = childLocs;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remarks = remarks;
		this.isDelete = isDelete;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "l_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="l_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="l_code")
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="l_order")
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="l_pid")
	public Location getParentLoc() {
		return parentLoc;
	}

	public void setParentLoc(Location parentLoc) {
		this.parentLoc = parentLoc;
	}

	@Column(name="l_state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(targetEntity=Location.class,cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="parentLoc")
	@Fetch(FetchMode.SUBSELECT)
	public List<Location> getChildLocs() {
		return childLocs;
	}

	public void setChildLocs(List<Location> childLocs) {
		this.childLocs = childLocs;
	}

	@Column(name="l_createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name="l_updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name="l_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="l_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "l_uid",referencedColumnName="u_id")
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
