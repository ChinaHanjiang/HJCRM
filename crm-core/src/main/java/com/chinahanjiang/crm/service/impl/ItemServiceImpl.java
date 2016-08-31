package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ItemDao;
import com.chinahanjiang.crm.dto.ItemDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.service.ItemService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("itemService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ItemServiceImpl implements ItemService {

	@Resource
	private ItemDao itemDao;
	
	@Override
	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row, Timestamp begin, Timestamp end, int i) {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		search.addFilterEqual("status", i);
		
		
		if(begin!=null){
			search.addFilterGreaterOrEqual("createTime", begin);
		}
		
		if(end!=null){
			search.addFilterLessOrEqual("createTime", end);
		}
		
		if(i != 0){
			search.addFilterEqual("status", i);
		}
		
		search.setMaxResults(row);
		search.setPage(page - 1 < 0 ? 0 : page - 1);
		SearchResult<Item> result = searchAndCount(search);
		List<Item> ils = result.getResult();

		List<ItemDto> ids = DataUtil.convertItemToDto(ils);
		int records = result.getTotalCount();

		SearchResultDto srd = new SearchResultDto();
		srd.getRows().clear();
		srd.getRows().addAll(ids);
		srd.setTotal(records);
		
		return srd;
	}

	private SearchResult<Item> searchAndCount(Search search) {
		
		return itemDao.searchAndCount(search);
	}

}
