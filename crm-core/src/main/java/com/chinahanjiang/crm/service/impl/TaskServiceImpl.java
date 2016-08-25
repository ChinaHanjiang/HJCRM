package com.chinahanjiang.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.TaskDao;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.TaskDto;
import com.chinahanjiang.crm.pojo.Task;
import com.chinahanjiang.crm.service.TaskService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("taskService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class TaskServiceImpl implements TaskService {

	@Resource
	private TaskDao taskDao;
	
	@Override
	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row) {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		
		search.setMaxResults(row);
		search.setPage(page - 1 < 0 ? 0 : page - 1);
		SearchResult<Task> result = searchAndCount(search);
		List<Task> cls = result.getResult();

		List<TaskDto> blds = DataUtil.convertTaskToDto(cls);
		int records = result.getTotalCount();

		SearchResultDto srd = new SearchResultDto();
		srd.getRows().clear();
		srd.getRows().addAll(blds);
		srd.setTotal(records);
		
		return srd;
	}

	private SearchResult<Task> searchAndCount(Search search) {
		
		return taskDao.searchAndCount(search);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(Task task) {
		
		return taskDao.save(task);
	}

}
