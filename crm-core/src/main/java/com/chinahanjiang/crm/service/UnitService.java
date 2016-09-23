package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.pojo.Unit;

public interface UnitService {

	List<Unit> findAllUnits();

	Unit findById(int unitId);

}
