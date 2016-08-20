package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.pojo.Groups;

public interface GroupsService {

	public void save(Groups group);

	public Groups findByName(String gName);

	public List<Groups> loadGroups();

	public Groups findById(int groupsId);
}
