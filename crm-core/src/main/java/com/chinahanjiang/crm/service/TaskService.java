package com.chinahanjiang.crm.service;

import java.sql.Timestamp;

import com.chinahanjiang.crm.dto.ItemDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.TaskDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Task;
import com.chinahanjiang.crm.pojo.User;

public interface TaskService {

	SearchResultDto searchAndCount(String order, String sort, int page, int row);
	
	boolean save(Task task);

	SearchResultDto searchAndCount(String order, String sort, int page,
			int row, Timestamp todayBegin, Timestamp todayEnd, int i);

	MessageDto update(TaskDto td, ItemDto id, UserDto u);

	MessageDto delete(TaskDto td);

	Task findById(int id);

}
