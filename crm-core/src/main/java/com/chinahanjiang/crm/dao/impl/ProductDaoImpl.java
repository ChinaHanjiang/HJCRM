package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.dao.ProductDao;
import com.chinahanjiang.crm.pojo.Product;

@Repository
public class ProductDaoImpl extends BaseDAO<Product, Integer>
		implements ProductDao {

}
