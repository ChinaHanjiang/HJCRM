package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.pojo.Customer;

public interface CustomerService {

	void save(Customer c);

	SearchResultDto searchAndCount(String order, String sort, int page, int row);
	
	Customer findById(int id);

}
