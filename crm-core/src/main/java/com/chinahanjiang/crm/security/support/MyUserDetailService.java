package com.chinahanjiang.crm.security.support;

import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chinahanjiang.crm.exception.LoginException;
import com.chinahanjiang.crm.exception.SystemRunException;
import com.chinahanjiang.crm.pojo.User;
import com.chinahanjiang.crm.service.RoleAuthoritiesService;
import com.chinahanjiang.crm.service.UserService;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleAuthoritiesService roleAuthoritiesService;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserCache userCache;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		
		User user = null;
		try {
			
			user = this.userService.findByUsersLogin(username);
			
		} catch (LoginException e) {
			
			throw new UsernameNotFoundException(username);
		} catch (SystemRunException e) {
			
			throw new UsernameNotFoundException(username);
		}
		
		if (user == null) {
			
			throw new UsernameNotFoundException(username);
		}else {
			if(!user.isEnabled()){
				throw new UsernameNotFoundException("该用户处于锁定状态");
			}
		}
		
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		
		//得到用户的权限
		auths = roleAuthoritiesService.loadUserAuthoritiesByName(username);
			
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setAuthorities(auths);
		
		return user;
	}

	public UserCache getUserCache() {
		return userCache;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RoleAuthoritiesService getRoleAuthoritiesService() {
		return roleAuthoritiesService;
	}

	public void setRoleAuthoritiesService(
			RoleAuthoritiesService roleAuthoritiesService) {
		this.roleAuthoritiesService = roleAuthoritiesService;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}