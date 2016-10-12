package com.chinahanjiang.crm.security.support;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.chinahanjiang.crm.pojo.UserRoles;

public interface MyUserDetails extends UserDetails {

	//用户id
	public int getId();

	//用户账户
	public String getCardName();

	//用户名
	public String getUserName();

	//用户密码
	public String getUserPassword();

	//用户描述或简介
	public String getRemarks();

	//用户是否能用
	public boolean getEnabled();

	//是否超级用户
	public Boolean getIssys();

	//用户职位
	public String getDuty();

	//用户分管的子系统
	public String getSubSystem();
	
	//用户相对应的角色集
	public Set<UserRoles> getUserRoles();
}
