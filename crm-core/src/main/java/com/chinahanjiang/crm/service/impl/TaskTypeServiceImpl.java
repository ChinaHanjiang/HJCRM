package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.TaskTypeDao;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.TaskTypeDto;
import com.chinahanjiang.crm.pojo.TaskType;
import com.chinahanjiang.crm.service.TaskTypeService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("taskTypeService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class TaskTypeServiceImpl implements TaskTypeService {

	@Resource
	private TaskTypeDao taskTypeDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(TaskType taskType) {
		
		return taskTypeDao.save(taskType);
	}
	
	@Override
	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row) {

		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		
		search.setMaxResults(row);
		search.setPage(page - 1 < 0 ? 0 : page - 1);
		SearchResult<TaskType> result = searchAndCount(search);
		List<TaskType> cls = result.getResult();

		List<TaskTypeDto> blds = DataUtil.convertTaskTypeToDto(cls);
		int records = result.getTotalCount();

		SearchResultDto srd = new SearchResultDto();
		srd.getRows().clear();
		srd.getRows().addAll(blds);
		srd.setTotal(records);
		
		return srd;
	}

	private SearchResult<TaskType> searchAndCount(Search search) {
		
		return taskTypeDao.searchAndCount(search);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto update(TaskTypeDto ttd) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		TaskType tt = null;
		int id = ttd.getId();
		
		if(id == 0){
			tt = new TaskType();
			tt.setCreateTime(now);
		} else {
			
			tt = findById(id);
			if(tt!=null){
				
				tt.setUpdateTime(now);
			}
		}
		
		if(tt!=null){
			
			tt.setName(ttd.getName());
			tt.setCode(ttd.getCode());
			tt.setRemarks(ttd.getRemarks());
			
			boolean isR = save(tt);
			if(isR){
				
				md.setT(true);
				md.setMessage("任务类别信息添加成功！");
			} else {
				
				md.setT(true);
				md.setMessage("任务类别信息修改成功！");
			}
			
		} else {
			
			md.setT(false);
			md.setMessage("任务类别信息添加失败！");
		}
		
		return md;
	}

	public TaskType findById(int id) {
		
		return taskTypeDao.find(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto delete(TaskTypeDto ttd) {

		MessageDto md = new MessageDto();
		TaskType tt = null;
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		int id = ttd.getId();
		if(id!=0){
			
			tt = findById(id);
			if(tt!=null){
				
				tt.setIsDelete(0);
				tt.setUpdateTime(now);

				save(tt);
				
				md.setT(true);
				md.setMessage("任务类别信息修改成功！");
				
			} else {
				
				md.setT(false);
				md.setMessage("任务类别在数据库不存在！");
			}
			
		} else {
			
			md.setT(false);
			md.setMessage("删除的任务类别的ID不能为空！");
		}
		
		return md;
	}

	@Override
	public MessageDto check(TaskTypeDto ttd) {

		MessageDto md = new MessageDto();
		TaskType tt = null;
		String message = "";
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		
		String name = ttd.getName();
		String code = ttd.getCode();
		
		if(!name.equals("") || !code.equals("")){
			
			if(!name.equals("")){
				
				search.addFilterEqual("name", name);
				message = "任务类别名称在数据库中存在，请重新确认！";
			}
				
			if(!code.equals("")){
				
				search.addFilterEqual("code", code);
				message = "任务类别编码在数据库中存在，请重新确认！";
			}
			
			try{
				
				tt = taskTypeDao.searchUnique(search);
			} catch(Exception e){
				
				e.printStackTrace();
			}
			
			if(tt!=null){
				
				md.setT(false);
				md.setMessage(message);
			} else {
				
				md.setT(true);
			}
		} else {
			
			md.setT(false);
			md.setMessage("任务类别名称或编码为空，请重新确认！");
		}
		
		return md;
	}

	@Override
	public List<TaskType> findAll() {
		
		return taskTypeDao.findAll();
	}

	
}
