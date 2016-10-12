package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.pojo.ProductAndQuoteRelation;
import com.chinahanjiang.crm.pojo.ProductQuoteDetails;


public interface ProductAndQuoteRelationService {

	List<ProductAndQuoteRelation> findByProductQuoteDetails(
			ProductQuoteDetails pqd);

	boolean removeById(int rid);

	ProductAndQuoteRelation findById(int id);

	boolean save(ProductAndQuoteRelation pqr);
}
