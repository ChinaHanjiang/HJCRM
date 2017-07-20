package com.chinahanjiang.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.AuthoritiesResourceDao;
import com.chinahanjiang.crm.service.AuthoritiesResourcesService;

@Service("authoritiesResourcesService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AuthoritiesResourcesServiceImpl implements
		AuthoritiesResourcesService {

	@Resource
	private AuthoritiesResourceDao authoritiesResourcesDao;
	
	@Override
	public List<String> findResourcesByAuthName(String auth) {
		// TODO Auto-generated method stub
		return null;
	}

}
