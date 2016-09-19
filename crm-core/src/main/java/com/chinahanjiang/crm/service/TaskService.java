package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.TaskDto;
import com.chinahanjiang.crm.dto.TaskTypeDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Task;

public interface TaskService {

	SearchResultDto searchAndCount(String order, String sort, int page, int row);

	boolean save(Task task);

	MessageDto update(TaskDto td, UserDto u);

	MessageDto delete(TaskDto td);

	Task findById(int id);

	String generateCode(TaskTypeDto ttd);

	MessageDto updateProducts(TaskDto td);

	MessageDto finishTask(TaskDto td);

	MessageDto giveupTask(TaskDto td);

	SearchResultDto searchAndCount(String order, String sort, int page,
			int row, String begin, String end, int status, int taskId,
			int tasktypeId, String name, String customerName);

}
