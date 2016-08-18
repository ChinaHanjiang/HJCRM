package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.pojo.Location;

public interface LocationService {

	public void save(Location loc);

	public Location findByName(String area);
	
	public String getAllLocations();
}
