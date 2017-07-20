package com.chinahanjiang.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.AuthoritiesDao;
import com.chinahanjiang.crm.service.AuthoritiesService;

@Service("authoritiesService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AuthoritiesServiceImpl implements AuthoritiesService {

	@Resource
	private AuthoritiesDao authoritiesDao;
	
	@Override
	public List<String> findAllAuthoritiesName() {
		return null;
	}

	
}
