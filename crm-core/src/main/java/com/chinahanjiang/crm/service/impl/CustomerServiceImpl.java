package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.CustomerDao;
import com.chinahanjiang.crm.dto.CustomerDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.pojo.Groups;
import com.chinahanjiang.crm.pojo.Location;
import com.chinahanjiang.crm.service.CustomerService;
import com.chinahanjiang.crm.service.GroupsService;
import com.chinahanjiang.crm.service.LocationService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("customerService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerDao customerDao;
	
	@Resource
	private LocationService locService;
	
	@Resource
	private GroupsService groupsService;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(Customer c) {
		// TODO Auto-generated method stub
		
		return customerDao.save(c);
	}

	@Override
	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row) {
		// TODO Auto-generated method stub
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
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

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto update(CustomerDto cd) {
		// TODO Auto-generated method stub
		
		MessageDto md = new MessageDto();
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		int locationId = cd.getLocId();
		Location loc = locService.findById(locationId);
		int groupsId = cd.getGroupsId();
		Groups groups = groupsService.findById(groupsId);
		
		int cId = cd.getId();
		Customer c = null;
		if(cId==0){
			
			c = new Customer();
		} else {
			
			c = findById(cId);
		}
		
		if(c!=null){
			
			c.setName(cd.getName());
			c.setCode(cd.getCode());
			c.setTelephone(cd.getTelephone());
			c.setFax(cd.getFax());
			c.setAddress(cd.getAddress());
			c.setRemarks(cd.getRemarks());
			c.setCreateTime(now);
			c.setGroups(groups);
			c.setLocation(loc);
			
			boolean isT = save(c);
			if(isT){
				
				md.setT(true);
				md.setMessage("公司客户添加成功！");
			} else {
				
				md.setT(true);
				md.setMessage("公司客户修改成功！");
			}
			
		} else {
			
			md.setT(false);
			md.setMessage("用户找不到！");
			
		}
		
		return md;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto delete(CustomerDto cd) {
		// TODO Auto-generated method stub
		
		MessageDto md = new MessageDto();
		int id = cd.getId();
		
		Customer c = customerDao.find(id);
		if(c!=null){
			
			c.setIsDelete(0);
			
			save(c);
			md.setT(true);
			md.setMessage("公司客户删除成功！");
			
		} else {
			
			md.setT(false);
			md.setMessage("用户找不到！");
		}
		
		
		return md;
	}
}
