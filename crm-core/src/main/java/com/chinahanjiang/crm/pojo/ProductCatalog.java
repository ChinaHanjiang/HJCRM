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
 * 产品种类
 * @author tree
 *
 */
@Entity
@Table(name = "ProductCatalog")
public class ProductCatalog {

	private int id;
	
	private String name;
	
	private String ename;
	
	private String code;//区域编码
	
	private int order;
	
	private ProductCatalog parentCatalog;
	
	private String state;
	
	private List<ProductCatalog> childPcs;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private String remarks;
	
	private int isDelete; /*0-删除,1-不删*/
	
	private User user;
	
	private int isF; /*0:no,1:yes*/
	
	public ProductCatalog(){
		
		this.isF = 0;
		this.isDelete = 1;
	}

	public ProductCatalog(int id, String name, String code, int order,
			ProductCatalog parentCatalog, String state, String ename,
			List<ProductCatalog> childPcs, Timestamp createTime,int isF,
			Timestamp updateTime, String remarks, int isDelete, User user) {
		super();
		this.id = id;
		this.name = name;
		this.ename = ename;
		this.code = code;
		this.order = order;
		this.parentCatalog = parentCatalog;
		this.state = state;
		this.childPcs = childPcs;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remarks = remarks;
		this.isDelete = isDelete;
		this.user = user;
		this.isF = isF;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pc_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="pc_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="pc_ename")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Column(name="pc_code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="pc_order")
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="pc_pid")
	public ProductCatalog getParentCatalog() {
		return parentCatalog;
	}

	public void setParentCatalog(ProductCatalog parentCatalog) {
		this.parentCatalog = parentCatalog;
	}

	@Column(name="pc_state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(targetEntity=ProductCatalog.class, cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="parentCatalog")
	@Fetch(FetchMode.SUBSELECT)
	public List<ProductCatalog> getChildPcs() {
		return childPcs;
	}

	public void setChildPcs(List<ProductCatalog> childPcs) {
		this.childPcs = childPcs;
	}

	@Column(name="pc_createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name="pc_upadteTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name="pc_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="pc_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pc_uid", referencedColumnName = "u_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name="pc_isF")
	public int getIsF() {
		return isF;
	}

	public void setIsF(int isF) {
		this.isF = isF;
	}
	
}
