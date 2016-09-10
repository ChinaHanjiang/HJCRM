package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.pojo.ItemType;


public interface ItemTypeService {

	List<ItemType> findAll();

	ItemType findById(int itemTypeId);

}
