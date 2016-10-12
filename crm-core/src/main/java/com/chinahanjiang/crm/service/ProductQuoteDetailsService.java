package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductConfigurationDto;

public interface ProductQuoteDetailsService {

	MessageDto saveProductMixForQuote(int itemId, int productId,
			String inserted, String updated, String deleted);

	List<ProductConfigurationDto> loadProductMixs(int productQuoteDetailId);

}
