package com.chinahanjiang.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ContactDao;
import com.chinahanjiang.crm.dto.ContactDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.pojo.Contact;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.service.ContactService;
import com.chinahanjiang.crm.service.CustomerService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("contactService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ContactServiceImpl implements ContactService {

	@Resource
	private ContactDao contactDao;
	
	@Resource
	private CustomerService customerService;
	
	@Override
	public SearchResultDto searchAndCount(int customerId, String order,
			String sort, int page, int row) {
		// TODO Auto-generated method stub
		
		Customer c = customerService.findById(customerId);
		Search search = new Search();
		search.addFilterEqual("customer", c);
		search.addFilterEqual("isDelete",1);
		search.setMaxResults(row);
		search.setPage(page - 1 < 0 ? 0 : page - 1);
		SearchResult<Contact> result = searchAndCount(search);
		List<Contact> cls = result.getResult();
		
		List<ContactDto> cds = DataUtil.convertContactToDto(cls);
		int records = result.getTotalCount();
		
		SearchResultDto srd = new SearchResultDto();
		srd.getRows().clear();
		srd.getRows().addAll(cds);
		srd.setTotal(records);
		
		return srd;
	}
	
	private SearchResult<Contact> searchAndCount(Search search) {

		return contactDao.searchAndCount(search);
	}
}
