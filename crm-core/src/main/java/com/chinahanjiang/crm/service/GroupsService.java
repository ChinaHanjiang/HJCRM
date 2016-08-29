package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.dto.DataListDto;
import com.chinahanjiang.crm.dto.GroupsDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.pojo.Groups;

public interface GroupsService {

	public boolean save(Groups group);

	public Groups findByName(String gName);

	public List<Groups> loadGroups();

	public Groups findById(int groupsId);

	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row);

	public MessageDto update(GroupsDto gd);

	public MessageDto delete(GroupsDto gd);

	public MessageDto check(GroupsDto gd);

	public List<DataListDto> search(GroupsDto gd);
}
