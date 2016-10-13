package com.chinahanjiang.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.UserRolesDao;
import com.chinahanjiang.crm.pojo.User;
import com.chinahanjiang.crm.pojo.UserRoles;
import com.chinahanjiang.crm.service.UserRolesService;
import com.chinahanjiang.crm.service.UserService;
import com.googlecode.genericdao.search.Search;

@Service("userRolesService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserRolesServiceImpl implements UserRolesService {

	@Resource
	private UserRolesDao userRolesDao;
	
	@Resource
	private UserService userService;
	
	@Override
	public List<UserRoles> findRolesByUser(User user) {
		return null;
	}

	@Override
	public List<UserRoles> findRolesByUserName(String username) {
		
		User user = userService.findUserByCardName(username);
		if(user!=null){
			
			Search search = new Search();
			search.addFilterEqual("isDelete", 1);
			search.addFilterEqual("user", user);
			return userRolesDao.search(search);
		} else {
			
			//用户找不到！ 
			return null;
		}
	}

	
}
