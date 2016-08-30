package com.chinahanjiang.crm.security.dao;

import java.util.List;

import com.chinahanjiang.crm.security.pojo.PubAuthorities;

public interface PubUsersDao {

	public List<PubAuthorities> findAuthByUserName(String username);

}
