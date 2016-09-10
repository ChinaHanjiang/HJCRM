package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.dao.ProductQuoteDetailsDao;
import com.chinahanjiang.crm.pojo.ProductQuoteDetails;

@Repository
public class ProductQuoteDetailsDaoImpl extends BaseDAO<ProductQuoteDetails, Integer>
		implements ProductQuoteDetailsDao {
}
