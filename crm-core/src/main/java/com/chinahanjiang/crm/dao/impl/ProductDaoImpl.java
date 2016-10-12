package com.chinahanjiang.crm.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.dao.ProductDao;
import com.chinahanjiang.crm.pojo.Product;

@Repository
public class ProductDaoImpl extends BaseDAO<Product, Integer>
		implements ProductDao {

	@Override
    @SuppressWarnings("unchecked")
	public List<Product> loadProducts(String hql,Object o){
    	
    	Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("tk", o);
		List<Product> objs = query.list();
		return objs;
    }

}
