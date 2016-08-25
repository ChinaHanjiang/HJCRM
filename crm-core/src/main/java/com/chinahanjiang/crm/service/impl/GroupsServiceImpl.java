package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.GroupsDao;
import com.chinahanjiang.crm.dto.GroupsDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.pojo.Groups;
import com.chinahanjiang.crm.service.GroupsService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("groupsService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class GroupsServiceImpl implements GroupsService {

	@Resource
	private GroupsDao groupsDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(Groups groups) {
		
		return groupsDao.save(groups);
	}

	@Override
	public Groups findByName(String gName) {
		
		Search search = new Search();
		search.addFilterEqual("name", gName);
		return groupsDao.searchUnique(search);
	}

	@Override
	public List<Groups> loadGroups() {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		return groupsDao.search(search);
	}

	@Override
	public Groups findById(int groupsId) {
		
		return groupsDao.find(groupsId);
	}

	@Override
	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row) {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		
		search.setMaxResults(row);
		search.setPage(page - 1 < 0 ? 0 : page - 1);
		SearchResult<Groups> result = searchAndCount(search);
		List<Groups> cls = result.getResult();

		List<GroupsDto> blds = DataUtil.convertGroupsToDto(cls);
		int records = result.getTotalCount();

		SearchResultDto srd = new SearchResultDto();
		srd.getRows().clear();
		srd.getRows().addAll(blds);
		srd.setTotal(records);
		
		return srd;
	}

	private SearchResult<Groups> searchAndCount(Search search) {
		
		return groupsDao.searchAndCount(search);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto update(GroupsDto gd) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Groups g = null;
		int id = gd.getId();
		
		if(id == 0){
			g = new Groups();
			g.setCreateTime(now);
		} else {
			
			g = findById(id);
			if(g!=null){
				
				g.setUpdateTime(now);
			}
		}
		
		if(g!=null){
			
			g.setName(gd.getName());
			g.setCode(gd.getCode());
			g.setRemarks(gd.getRemarks());
			
			boolean isR = save(g);
			if(isR){
				
				md.setT(true);
				md.setMessage("集团信息添加成功！");
			} else {
				
				md.setT(true);
				md.setMessage("集团信息修改成功！");
			}
			
		} else {
			
			md.setT(false);
			md.setMessage("集团信息添加失败！");
		}
		
		return md;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto delete(GroupsDto gd) {
		
		MessageDto md = new MessageDto();
		Groups g = null;
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		int id = gd.getId();
		if(id!=0){
			
			g = findById(id);
			if(g!=null){
				
				g.setIsDelete(0);
				g.setUpdateTime(now);

				save(g);
				
				md.setT(true);
				md.setMessage("集团信息修改成功！");
				
			} else {
				
				md.setT(false);
				md.setMessage("集团信息在数据库不存在！");
			}
			
		} else {
			
			md.setT(false);
			md.setMessage("删除的集团的ID不能为空！");
		}
		
		return md;
	}

	@Override
	
	public MessageDto check(GroupsDto gd) {
		
		MessageDto md = new MessageDto();
		Groups g = null;
		String message = "";
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		
		String name = gd.getName();
		String code = gd.getCode();
		
		if(!name.equals("") || !code.equals("")){
			
			if(!name.equals("")){
				
				search.addFilterEqual("name", name);
				message = "集团名称在数据库中存在，请重新确认！";
			}
				
			if(!code.equals("")){
				
				search.addFilterEqual("code", code);
				message = "集团编码在数据库中存在，请重新确认！";
			}
			
			try{
				
				g = groupsDao.searchUnique(search);
			} catch(Exception e){
				
				e.printStackTrace();
			}
			
			if(g!=null){
				
				md.setT(false);
				md.setMessage(message);
			} else {
				
				md.setT(true);
			}
		} else {
			
			md.setT(false);
			md.setMessage("区域名称或编码为空，请重新确认！");
		}
		
		return md;
	}
}
