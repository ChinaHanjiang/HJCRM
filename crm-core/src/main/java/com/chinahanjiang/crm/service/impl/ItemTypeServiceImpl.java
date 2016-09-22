package com.chinahanjiang.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ItemTypeDao;
import com.chinahanjiang.crm.pojo.ItemType;
import com.chinahanjiang.crm.service.ItemTypeService;
import com.googlecode.genericdao.search.Search;

@Service("itemTypeService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ItemTypeServiceImpl implements ItemTypeService {

	@Resource
	private ItemTypeDao itemTypeDao;

	@Override
	public List<ItemType> findAll() {
		
		return itemTypeDao.findAll();
	}

	@Override
	public ItemType findById(int itemTypeId) {
		
		return itemTypeDao.find(itemTypeId);
	}

	@Override
	public ItemType findByName(String name) {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		search.addFilterEqual("name", name);
		ItemType it = itemTypeDao.searchUnique(search);
		
		return it;
	}

}
