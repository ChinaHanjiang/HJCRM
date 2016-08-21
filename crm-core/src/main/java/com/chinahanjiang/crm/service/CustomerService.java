package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.CustomerDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.pojo.Customer;

public interface CustomerService {

	boolean save(Customer c);

	Customer findById(int id);

	MessageDto update(CustomerDto cd);

	MessageDto delete(CustomerDto cd);

	SearchResultDto searchAndCount(int locId, String order, String sort,
			int page, int row);

}
