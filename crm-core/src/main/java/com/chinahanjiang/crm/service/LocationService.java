package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.dto.LocationDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.pojo.Location;

public interface LocationService {

	public void save(Location loc);

	public Location findByName(String area);
	
	public String getAllLocations();

	public List<Location> loadLocationsByPid(int i);

	public String getLocationsByFid(int pid);

	public String getParentLocById(int id);

	public Location findById(int locationId);

	public MessageDto update(LocationDto ld);

	public MessageDto delete(LocationDto ld);

	public MessageDto check(LocationDto ld);
}
