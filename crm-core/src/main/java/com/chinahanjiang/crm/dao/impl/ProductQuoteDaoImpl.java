package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.dao.ProductQuoteDao;
import com.chinahanjiang.crm.pojo.ProductQuote;

@Repository
public class ProductQuoteDaoImpl extends BaseDAO<ProductQuote, Integer>
		implements ProductQuoteDao {
}
