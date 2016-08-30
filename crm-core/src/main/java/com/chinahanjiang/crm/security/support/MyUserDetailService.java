package com.chinahanjiang.crm.security.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chinahanjiang.crm.security.dao.PubAuthoritiesResourcesDao;
import com.chinahanjiang.crm.security.dao.PubUsersDao;
import com.chinahanjiang.crm.security.pojo.PubAuthorities;
import com.chinahanjiang.crm.security.pojo.PubAuthoritiesResources;
/**
 * 该类的主要作用是spring security提供一个经过用户认证后的UserDetails
 * 该UserDetails包括用户名、密码、是否可用、是否过期等信息。
 * @author tree
 *
 */

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private PubUsersDao pubUserDao;
	
	@Autowired
	private PubAuthoritiesResourcesDao pubAuthoritiesResourcesDao;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserCache userCache;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		//获取用户的权限
		List<PubAuthorities> auth = pubUserDao.findAuthByUserName(username);
		String password = null;
		//取得用户密码
		password = pubUserDao.findAuthByUserName(username).get(0).getUserPassword();
		List<PubAuthoritiesResources> aaa = pubAuthoritiesResourcesDao.getAll();
		
		User user = new User(username,password,true,true,true,true, auths);
		return user;
	}

	public PubUsersDao getPubUserDao() {
		return pubUserDao;
	}

	public void setPubUserDao(PubUsersDao pubUserDao) {
		this.pubUserDao = pubUserDao;
	}

	public PubAuthoritiesResourcesDao getPubAuthoritiesResourcesDao() {
		return pubAuthoritiesResourcesDao;
	}

	public void setPubAuthoritiesResourcesDao(
			PubAuthoritiesResourcesDao pubAuthoritiesResourcesDao) {
		this.pubAuthoritiesResourcesDao = pubAuthoritiesResourcesDao;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public UserCache getUserCache() {
		return userCache;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}
}