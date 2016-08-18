package com.chinahanjiang.crm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.service.TaskService;

@Service("taskService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class TaskServiceImpl implements TaskService {

}