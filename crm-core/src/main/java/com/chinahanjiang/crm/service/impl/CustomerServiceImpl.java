package com.chinahanjiang.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.CustomerDao;
import com.chinahanjiang.crm.dto.CustomerDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.service.CustomerService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("customerService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerDao customerDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(Customer c) {
		// TODO Auto-generated method stub
		customerDao.save(c);
	}

	@Override
	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row) {
		// TODO Auto-generated method stub
		
		Search search = new Search();
		search.setMaxResults(row);
		search.setPage(page - 1 < 0 ? 0 : page - 1);
		SearchResult<Customer> result = searchAndCount(search);
		List<Customer> cls = result.getResult();

		List<CustomerDto> blds = DataUtil.convertCustomerToDto(cls);
		int records = result.getTotalCount();

		SearchResultDto srd = new SearchResultDto();
		srd.getRows().clear();
		srd.getRows().addAll(blds);
		srd.setTotal(records);
		
		return srd;
	}

	private SearchResult<Customer> searchAndCount(Search search) {

		return customerDao.searchAndCount(search);
	}

	@Override
	public Customer findById(int id) {
		// TODO Auto-generated method stub
		
		Customer c = customerDao.find(id);
		return c;
	}
}
