package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.dao.UserDao;
import com.chinahanjiang.crm.pojo.User;

@Repository
public class UserDaoImpl extends BaseDAO<User, Integer> implements
UserDao {

}
