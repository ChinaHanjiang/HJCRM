package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.pojo.Task;

public interface TaskService {

	SearchResultDto searchAndCount(String order, String sort, int page, int row);
	
	boolean save(Task task);
}
