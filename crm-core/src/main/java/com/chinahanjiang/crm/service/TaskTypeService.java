package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.TaskTypeDto;
import com.chinahanjiang.crm.pojo.TaskType;

public interface TaskTypeService {

	boolean save(TaskType taskType); 
	
	SearchResultDto searchAndCount(String order, String sort, int page, int row);

	MessageDto update(TaskTypeDto ttd);

	MessageDto delete(TaskTypeDto ttd);

	MessageDto check(TaskTypeDto ttd);
	
	public TaskType findById(int id);

	List<TaskType> findAll();

}
