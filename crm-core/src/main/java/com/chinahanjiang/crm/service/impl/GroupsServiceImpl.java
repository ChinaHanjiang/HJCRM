package com.chinahanjiang.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.GroupsDao;
import com.chinahanjiang.crm.pojo.Groups;
import com.chinahanjiang.crm.service.GroupsService;
import com.googlecode.genericdao.search.Search;

@Service("groupsService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class GroupsServiceImpl implements GroupsService {

	@Resource
	private GroupsDao groupsDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(Groups groups) {
		// TODO Auto-generated method stub
		
		groupsDao.save(groups);
	}

	@Override
	public Groups findByName(String gName) {
		// TODO Auto-generated method stub
		
		Search search = new Search();
		search.addFilterEqual("name", gName);
		return groupsDao.searchUnique(search);
	}

	@Override
	public List<Groups> loadGroups() {
		// TODO Auto-generated method stub
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		return groupsDao.search(search);
	}

	@Override
	public Groups findById(int groupsId) {
		// TODO Auto-generated method stub
		
		return groupsDao.find(groupsId);
	}
	
	
}
