package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.dto.CustomerDto;
import com.chinahanjiang.crm.dto.DataListDto;
import com.chinahanjiang.crm.dto.LocationDto;
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

	List<Customer> loadCustomers(int i);

	List<DataListDto> search(CustomerDto cd);

	String generateCode(LocationDto ld);

}
