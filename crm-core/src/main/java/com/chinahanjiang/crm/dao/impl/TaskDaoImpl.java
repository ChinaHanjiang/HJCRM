package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.dao.TaskDao;
import com.chinahanjiang.crm.pojo.Task;

@Repository
public class TaskDaoImpl extends BaseDAO<Task, Integer> implements TaskDao {

}
