package com.chinahanjiang.crm.service.impl;

import java.util.List;

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

	@Override
	public List<Location> loadLocationsByPid(int i) {
		// TODO Auto-generated method stub
		
		Location pLocation = locDao.find(i);
		
		Search search = new Search();
		search.addFilterEqual("parentLoc", pLocation);
		List<Location> locations = locDao.search(search);
		
		return locations;
	}

	@Override
	public String getLocationsByFid(int pid) {
		// TODO Auto-generated method stub
		
		Location pLocation = locDao.find(pid);
		String comboStr = DataUtil.locationCrdToJson(pLocation);
		return comboStr;
	}

	@Override
	public String getParentLocById(int id) {
		// TODO Auto-generated method stub
		
		String result = "";
		
		Location location = locDao.find(id);
		Location fLoc = location.getParentLoc();
		Location gfLoc = fLoc.getParentLoc();
		
		if(fLoc!=null && gfLoc!=null)
			result += gfLoc.getId() + "-" + fLoc.getId();
		
		return result;
	}

	@Override
	public Location findById(int locationId) {
		// TODO Auto-generated method stub
		
		return locDao.find(locationId);
	}
	
	
}
