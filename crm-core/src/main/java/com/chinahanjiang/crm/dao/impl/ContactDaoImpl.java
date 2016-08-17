package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.dao.ContactDao;
import com.chinahanjiang.crm.pojo.Contact;

@Repository
public class ContactDaoImpl extends BaseDAO<Contact, Integer> implements
ContactDao {

}
