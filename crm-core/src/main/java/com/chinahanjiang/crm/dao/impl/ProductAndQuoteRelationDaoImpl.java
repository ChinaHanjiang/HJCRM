package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.dao.ProductAndQuoteRelationDao;
import com.chinahanjiang.crm.pojo.ProductAndQuoteRelation;

@Repository
public class ProductAndQuoteRelationDaoImpl extends
		BaseDAO<ProductAndQuoteRelation, Integer> implements
		ProductAndQuoteRelationDao {
}
