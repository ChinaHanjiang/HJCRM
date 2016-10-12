package com.chinahanjiang.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.UnitDao;
import com.chinahanjiang.crm.pojo.Unit;
import com.chinahanjiang.crm.service.UnitService;

@Service("unitService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UnitServiceImpl implements UnitService {

	@Resource
	private UnitDao unitDao;
	
	@Override
	public List<Unit> findAllUnits(){
		
		return unitDao.findAll();
	}

	@Override
	public Unit findById(int id) {
		
		return unitDao.find(id);
	}
	
}
