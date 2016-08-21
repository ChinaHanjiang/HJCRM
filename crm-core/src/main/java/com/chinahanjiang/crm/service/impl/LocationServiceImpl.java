package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.LocationDao;
import com.chinahanjiang.crm.dto.LocationDto;
import com.chinahanjiang.crm.dto.MessageDto;
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
		
		locDao.save(loc);
	}

	@Override
	public Location findByName(String area) {
		
		Search search = new Search();
		search.addFilterEqual("name", area);
		return locDao.searchUnique(search);
	}

	@Override
	public String getAllLocations() {
		
		Location loc = locDao.find(1);
		String locTree = DataUtil.locationToJson(loc);
		return locTree;
	}

	@Override
	public List<Location> loadLocationsByPid(int i) {
		
		Location pLocation = locDao.find(i);
		
		Search search = new Search();
		search.addFilterEqual("parentLoc", pLocation);
		List<Location> locations = locDao.search(search);
		
		return locations;
	}

	@Override
	public String getLocationsByFid(int pid) {
		
		Location pLocation = locDao.find(pid);
		String comboStr = DataUtil.locationCrdToJson(pLocation);
		return comboStr;
	}

	@Override
	public String getParentLocById(int id) {
		
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
		
		return locDao.find(locationId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto update(LocationDto ld) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Location location = null;
		
		Location pLocation = findById(ld.getParentId());
		if(pLocation==null){
			
			md.setMessage("上级区域信息不存在！");
			md.setT(false);
			return md;
		}
		
		int id = ld.getId();
		if(id == 0){
			
			location = new Location();
			location.setCreateTime(now);
			md.setMessage("区域信息添加成功！");
		} else {
			
			location = findById(id);
			
			if(location != null)
				location.setUpdateTime(now);
			
			md.setMessage("区域信息修改成功！");
		}
		
		if(location != null){
			
			location.setName(ld.getName());
			location.setCode(ld.getCode());
			
			location.setParentLoc(pLocation);
			
			save(location);
			
			md.setIntF(location.getId());
			md.setT(true);
			
		} else {
			
			md.setT(false);
			md.setMessage("区域位置在数据库不存在");
		}
		
		return md;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto delete(LocationDto ld) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Location l = new Location();
		int id = ld.getId();
		int parentId = ld.getParentId();
		
		if(id == 0 && parentId == 0){
			
			md.setMessage("删除区域信息不全，删除失败!");
			md.setT(false);
			return md;
			
		} else if(id != 0){
			
			l = findById(id);
			if(l!=null){
				
				l.setIsDelete(0);
				l.setUpdateTime(now);
				save(l);
				
				md.setMessage("删除子区域成功！");
				md.setT(true);
			} else {
				
				md.setMessage("子区域信息不存在！");
				md.setT(false);
			}
			return md;
			
		} else if(parentId != 0){
			
			l = findById(id);
			if(l!=null){
				
				l.setIsDelete(0);
				l.setUpdateTime(now);
				
				List<Location> childLoc = l.getChildLocs();
				if(childLoc!=null){
					
					Iterator<Location> it = childLoc.iterator();
					while(it.hasNext()){
						
						Location pLoc = it.next();
						pLoc.setIsDelete(0);
						pLoc.setUpdateTime(now);
						
						save(pLoc);
					}
				}
				save(l);
				
				md.setMessage("删除父区域成功！");
				md.setT(true);
				
			} else {
				
				md.setMessage("父区域信息不存在！");
				md.setT(false);
			}
			return md;
		}
		return md;
	}

	@Override
	public MessageDto check(LocationDto ld) {
		
		MessageDto md = new MessageDto();
		String name = ld.getName();
		String code = ld.getCode();
		Location parentLoc = null;
		String message = "";
		
		int parentId = ld.getParentId();
		if(parentId!=0){
			
			parentLoc = findById(parentId);
		}
		
		if(parentLoc == null){
			
			md.setT(false);
			md.setMessage("父区域不存在！");
			
		} else {
			
			Search search = new Search();
			search.addFilterEqual("parentLoc", parentLoc);
			search.addFilterEqual("isDelete", 1);
			if(name!=null && !name.equals("")){
				
				search.addFilterEqual("name", name);
				message = "区域名称数据库中存在，请重新确认！";
			}
				
			if(code!=null && !code.equals("")){
				
				search.addFilterEqual("code", code);
				message = "区域编码数据库中存在，请重新确认！";
			}
			
			Location loc = null;
			try{
				loc = locDao.searchUnique(search);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(loc==null){
				
				md.setT(true);
				md.setMessage("在数据库中不存在，可以录入！");
				
			} else {
				
				md.setT(false);
				md.setMessage(message);
			}
		}
		
		return md;
	}
}
