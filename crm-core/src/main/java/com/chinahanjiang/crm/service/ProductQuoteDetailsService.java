package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.MessageDto;

public interface ProductQuoteDetailsService {

	MessageDto saveProductMixForQuote(int quoteId, int productId,
			String inserted, String updated, String deleted);

}
