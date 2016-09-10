package com.chinahanjiang.crm.dao;

import java.util.List;

import com.chinahanjiang.crm.pojo.Product;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface ProductDao extends GenericDAO<Product, Integer> {

	List<Product> loadProducts(String hql,Object o);
}
