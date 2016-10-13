package com.chinahanjiang.crm.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.RoleAuthoritiesDao;
import com.chinahanjiang.crm.pojo.Role;
import com.chinahanjiang.crm.pojo.RoleAuthorities;
import com.chinahanjiang.crm.pojo.UserRoles;
import com.chinahanjiang.crm.service.RoleAuthoritiesService;
import com.chinahanjiang.crm.service.UserRolesService;
import com.googlecode.genericdao.search.Search;

@Service("roleAuthoritiesService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class RoleAuthoritiesServiceImpl implements RoleAuthoritiesService {

	@Resource
	private RoleAuthoritiesDao roleAuthoritiesDao;
	
	@Resource
	private UserRolesService userRolesService;
	
	@Override
	public Collection<GrantedAuthority> loadUserAuthoritiesByName(
			String username) {

		try {

			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			List<String> query1 = loadUserAuthorities(username);
			for (String roleName : query1) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
				auths.add(authority);
			}

			return auths;

		} catch (RuntimeException re) {
			//log.error("find by authorities by username failed.", re);
			throw re;
		}
	}

	private List<String> loadUserAuthorities(String username) {
		
		List<String> auNames = new ArrayList<String>();
		List<UserRoles> userRoles = userRolesService.findRolesByUserName(username);
		List<Role> roles = new ArrayList<Role>(); 
		Iterator<UserRoles> it = userRoles.iterator();
		while(it.hasNext()){
			
			UserRoles ur = it.next();
			roles.add(ur.getRole());
		}
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		search.addFilterIn("role", roles);
		
		List<RoleAuthorities> raus = roleAuthoritiesDao.search(search);
		Iterator<RoleAuthorities> rsuit = raus.iterator();
		while(rsuit.hasNext()){
			
			RoleAuthorities rau = rsuit.next();
			auNames.add(rau.getAuthorities().getName());
		}
		
		return auNames;
	}

}
