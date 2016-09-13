package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.ProductQuote;
import com.chinahanjiang.crm.pojo.ProductQuoteDetails;

public interface ProductQuoteService {

	MessageDto add(int itemId, UserDto ud);

	List<ProductQuoteDetails> findProductQuoteDetailsByItem(Item item);

	ProductQuote findProductQuoteByItem(Item item);

}
