package com.chinahanjiang.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.LocationDao;
import com.chinahanjiang.crm.pojo.Location;
import com.chinahanjiang.crm.service.LocationService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;

@Service("locationService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class LocationServiceImpl implements LocationService {

	@Resource
	private LocationDao locDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(Location loc) {
		// TODO Auto-generated method stub
		
		locDao.save(loc);
	}

	@Override
	public Location findByName(String area) {
		// TODO Auto-generated method stub
		
		Search search = new Search();
		search.addFilterEqual("name", area);
		return locDao.searchUnique(search);
	}

	@Override
	public String getAllLocations() {
		// TODO Auto-generated method stub
		
		Location loc = locDao.find(1);
		String locTree = DataUtil.locationToJson(loc);
		return locTree;
	}
	
	
}
