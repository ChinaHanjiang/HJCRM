package com.chinahanjiang.crm.util;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinahanjiang.crm.dto.LocationDto;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.pojo.Location;
import com.chinahanjiang.crm.service.CustomerService;
import com.chinahanjiang.crm.service.LocationService;
import com.chinahanjiang.crm.service.impl.CustomerServiceImpl;
import com.chinahanjiang.crm.service.impl.LocationServiceImpl;

public class ExcelUtilTest {

	private ApplicationContext context;

	@Before
	public void runContext() {

		context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
	}
	
	@Test
	public void testImportCustomer() throws IOException{
		
		CustomerService cs = (CustomerServiceImpl) context
				.getBean("customerService");
		LocationService ls = (LocationServiceImpl) context
				.getBean("locationService");
		
		File f = new File("C:\\Users\\tree\\Desktop\\客户关系管理\\40基础资料\\customer.xls");
		List<Customer> customers = ExcelUtil.readCustomerInfo(f);
		
		Iterator<Customer> it = customers.iterator();
		while(it.hasNext()){
			
			Customer c = it.next();
			
			Location l = c.getLocation();
			if(l!=null){
				
				String area = l.getName();
				Location loc = ls.findByName(area);
				if(loc!=null){
					c.setLocation(loc);
				}
			}
			
			cs.save(c);
			
		}
		
	}
	
	@Test
	public void testImportLocations() throws IOException{
		
		LocationService ls = (LocationServiceImpl) context
				.getBean("locationService");
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		File f = new File("C:\\Users\\tree\\Desktop\\客户关系管理\\40基础资料\\customer.xls");
		List<LocationDto> lds = ExcelUtil.readLocationInfo(f);
		
		Location fl = new Location();
		fl.setName("区域分类");
		fl.setCreateTime(now);
		fl.setIsDelete(0);
		List<Location> flls = new ArrayList<Location>();
		
		Map<String,Map<String,List<String>>> maps = new HashMap<String,Map<String,List<String>>>();
		Map<String,List<String>> m = null;
		List<String> t = null;
		
		for(LocationDto d : lds){
			
			String farea = d.getfArea();
			String sarea = d.getsArea();
			String tarea = d.gettArea();
			
			boolean i = maps.containsKey(farea);
			if(i){
				m = maps.get(farea);
			} else {
				m = new HashMap<String,List<String>>();
			}
			
			boolean j = m.containsKey(sarea);
			if(j){
				t = m.get(sarea);
			} else {
				t = new ArrayList<String>();
			}
			
			t.add(tarea);
			m.put(sarea, t);
			maps.put(farea, m);
		}
		
		Set<String> s = maps.keySet();
		Iterator<String> it = s.iterator();
		while(it.hasNext()){
			
			Location floc = new Location();
			String fs = it.next();
			floc.setName(fs);
			floc.setCreateTime(now);
			floc.setIsDelete(0);
			floc.setParentLoc(fl);
			flls.add(floc);
			
			List<Location> fcls = new ArrayList<Location>();
			
			Map<String,List<String>> map = maps.get(fs);
			Set<String> fset = map.keySet();
			Iterator<String> fit = fset.iterator();
			while(fit.hasNext()){
				
				String ss = fit.next();
				Location sloc = new Location();
				sloc.setName(ss);
				sloc.setParentLoc(floc);
				sloc.setCreateTime(now);
				sloc.setIsDelete(0);
				
				fcls.add(sloc);
				List<Location> scls = new ArrayList<Location>();
				
				List<String> slist = map.get(ss);
				Iterator<String> sit = slist.iterator();
				while(sit.hasNext()){
					
					String ts = sit.next();
					Location tloc = new Location();
					tloc.setName(ts);
					tloc.setParentLoc(sloc);
					tloc.setCreateTime(now);
					tloc.setIsDelete(0);
					
					scls.add(tloc);
					
				}
				
				sloc.setChildLocs(scls);
			}
			
			floc.setChildLocs(fcls);
		}
		
		fl.setChildLocs(flls);
		ls.save(fl);
	}
}
