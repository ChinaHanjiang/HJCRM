package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.dao.ItemDao;
import com.chinahanjiang.crm.pojo.Item;

@Repository
public class ItemDaoImpl extends BaseDAO<Item, Integer> implements ItemDao {

}
