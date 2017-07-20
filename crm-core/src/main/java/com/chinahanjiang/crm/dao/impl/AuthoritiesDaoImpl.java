package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.AuthoritiesDao;
import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.pojo.Authorities;

@Repository
public class AuthoritiesDaoImpl extends BaseDAO<Authorities, Integer> implements
		AuthoritiesDao {

}
