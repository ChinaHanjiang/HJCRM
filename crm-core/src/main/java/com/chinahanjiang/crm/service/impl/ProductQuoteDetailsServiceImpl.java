package com.chinahanjiang.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ProductQuoteDetailsDao;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.service.ProductQuoteDetailsService;

@Service("productQuoteDetailsService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProductQuoteDetailsServiceImpl implements
		ProductQuoteDetailsService {

	@Resource
	private ProductQuoteDetailsDao productQuoteDetailsDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto saveProductMixForQuote(int quoteId, int productId,
			String inserted, String updated, String deleted) {
		
		return null;
	}
	
	
}
