package com.chinahanjiang.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinahanjiang.crm.dao.BaseDAO;
import com.chinahanjiang.crm.dao.LocationDao;
import com.chinahanjiang.crm.pojo.Location;

@Repository
public class LocationDaoImpl extends BaseDAO<Location, Integer> implements
		LocationDao {

}
