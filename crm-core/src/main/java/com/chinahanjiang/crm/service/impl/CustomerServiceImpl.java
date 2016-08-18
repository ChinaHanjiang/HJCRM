package com.chinahanjiang.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.CustomerDao;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.service.CustomerService;

@Service("customerService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerDao customerDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(Customer c) {
		// TODO Auto-generated method stub
		
		customerDao.save(c);
	}

}
