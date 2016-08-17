package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.dao.CustomerDao;
import com.chinahanjiang.crm.pojo.Customer;

@Repository
public class CustomerDaoImpl extends BaseDAO<Customer, Integer> implements
CustomerDao {

}
