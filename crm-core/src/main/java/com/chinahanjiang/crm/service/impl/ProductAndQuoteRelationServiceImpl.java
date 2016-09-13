package com.chinahanjiang.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ProductAndQuoteRelationDao;
import com.chinahanjiang.crm.pojo.ProductAndQuoteRelation;
import com.chinahanjiang.crm.pojo.ProductQuoteDetails;
import com.chinahanjiang.crm.service.ProductAndQuoteRelationService;
import com.googlecode.genericdao.search.Search;

@Service("productAndQuoteRelationService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProductAndQuoteRelationServiceImpl implements
		ProductAndQuoteRelationService {

	@Resource
	private ProductAndQuoteRelationDao producatAndQuoteRelationDao;

	@Override
	public List<ProductAndQuoteRelation> findByProductQuoteDetails(
			ProductQuoteDetails pqd) {

		List<ProductAndQuoteRelation> pqrs = new ArrayList<ProductAndQuoteRelation>();

		if (pqd != null) {

			Search search = new Search();
			search.addFilterEqual("isDelete", 1);
			search.addFilterEqual("productQuoteDetails", pqd);

			List<ProductAndQuoteRelation> paqrs = producatAndQuoteRelationDao
					.search(search);
			
			if(paqrs!=null){
				
				pqrs.addAll(paqrs);
			}
		}

		return pqrs;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean removeById(int rid) {
		
		return producatAndQuoteRelationDao.removeById(rid);
	}

	@Override
	public ProductAndQuoteRelation findById(int id) {
		
		return producatAndQuoteRelationDao.find(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(ProductAndQuoteRelation pqr) {
		
		return producatAndQuoteRelationDao.save(pqr);
	}

}
