package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.AuthoritiesResourceDao;
import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.pojo.AuthoritieResources;

@Repository
public class AuthoritieResourcesDaoImpl extends
		BaseDAO<AuthoritieResources, Integer> implements AuthoritiesResourceDao {

}
