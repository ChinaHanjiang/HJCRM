package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.CustomerDao;
import com.chinahanjiang.crm.dto.CustomerDto;
import com.chinahanjiang.crm.dto.DataListDto;
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
		
		return customerDao.save(c);
	}

	private SearchResult<Customer> searchAndCount(Search search) {

		return customerDao.searchAndCount(search);
	}

	@Override
	public Customer findById(int id) {
		
		Customer c = customerDao.find(id);
		return c;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto update(CustomerDto cd) {
		
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
			c.setCreateTime(now);
		} else {
			
			c = findById(cId);
			
			if(c!=null)
				
				c.setUpdateTime(now);
		}
		
		if(c!=null){
			
			c.setName(cd.getName());
			c.setCode(cd.getCode());
			c.setTelephone(cd.getTelephone());
			c.setFax(cd.getFax());
			c.setAddress(cd.getAddress());
			c.setRemarks(cd.getRemarks());
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
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		MessageDto md = new MessageDto();
		int id = cd.getId();
		
		Customer c = customerDao.find(id);
		if(c!=null){
			
			c.setIsDelete(0);
			c.setUpdateTime(now);
			
			save(c);
			md.setT(true);
			md.setMessage("公司客户删除成功！");
			
		} else {
			
			md.setT(false);
			md.setMessage("用户找不到！");
		}
		
		
		return md;
	}

	@Override
	public SearchResultDto searchAndCount(int locId, String order, String sort,
			int page, int row) {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		
		Location c = locService.findById(locId);
		if(c!=null){
			
			List<Location> childLocs = DataUtil.getAllLocChildren(c);
			search.addFilterIn("location", childLocs);
		}
		
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

	@Override
	public List<Customer> loadCustomers(int i) {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		search.setMaxResults(i);
		search.setPage(0);
		SearchResult<Customer> result = searchAndCount(search);
		List<Customer> cls = result.getResult();
		return cls;
	}

	@Override
	public List<DataListDto> search(CustomerDto cd) {
		
		String name = cd.getName();
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		search.addFilterLike("name", "%" + name + "%");
		SearchResult<Customer> result = searchAndCount(search);
		List<Customer> cls = result.getResult();
		List<DataListDto> dlds = DataUtil.convertCustomerToDld(cls);
		
		return dlds;
	}
	
}
