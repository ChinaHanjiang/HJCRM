package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.dao.TaskTypeDao;
import com.chinahanjiang.crm.pojo.TaskType;

@Repository
public class TaskTypeDaoImpl extends BaseDAO<TaskType, Integer> implements
		TaskTypeDao {

}
