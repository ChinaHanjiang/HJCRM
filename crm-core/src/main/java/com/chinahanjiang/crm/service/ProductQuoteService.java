package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductQuoteDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.ProductQuote;
import com.chinahanjiang.crm.pojo.ProductQuoteDetails;

public interface ProductQuoteService {

	boolean save(ProductQuote pq);
	
	MessageDto add(int itemId, UserDto ud);

	List<ProductQuoteDetails> findProductQuoteDetailsByItem(Item item);

	ProductQuote findProductQuoteByItem(Item item);

	void deleteQuoteByItem(Item i);

	MessageDto findProductQuoteByItemId(int itemId);

	SearchResultDto searchAndCount(int taskId, String order, String sort,
			int page, int row);

	MessageDto closeProductQuote(ProductQuoteDto pqd);

}
