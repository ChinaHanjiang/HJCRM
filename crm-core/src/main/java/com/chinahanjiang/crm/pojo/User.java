package com.chinahanjiang.crm.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户 2016-8-15
 * @author tree
 *
 */
@Entity
@Table(name="user")
public class User {

	private int id;
	
	private String cardName;
	
	private String name;
	
	private String password;
	
	private String duty;
	
	private String mobilephone;
	
	private String email;
	
	private int sex;
	
	private int isDelete; /*0-删除,1-没删除*/
	
	private String remarks;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	public User(){
		
		this.isDelete = 1;
	}

	public User(int id, String cardName, String name, String password, String duty,
			String mobilephone, String email, int sex, int isDelete, String remarks,
			Timestamp createTime, Timestamp updateTime) {
		super();
		this.id = id;
		this.cardName = cardName;
		this.name = name;
		this.password = password;
		this.duty = duty;
		this.mobilephone = mobilephone;
		this.email = email;
		this.sex = sex;
		this.isDelete = isDelete;
		this.remarks = remarks;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "u_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="u_cardName")
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	@Column(name="u_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="u_password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="u_duty")
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Column(name="u_mobilephone")
	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	@Column(name="u_email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="u_sex")
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Column(name="u_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name="u_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="u_createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name="u_updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
