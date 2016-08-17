package com.chinahanjiang.crm.util;

import java.io.File;
import java.io.IOException;
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
import com.chinahanjiang.crm.pojo.Location;

public class ExcelUtilTest {

	private ApplicationContext context;

	@Before
	public void runContext() {

		context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
	}
	
	@Test
	public void testImportLocations() throws IOException{
		
		File f = new File("C:\\Users\\tree\\Desktop\\客户关系管理\\40基础资料\\customer.xls");
		List<LocationDto> lds = ExcelUtil.readLocationInfo(f);
		System.out.println(lds.size());
		Location loc = null;
		List<Location> sloc = null;
		List<Location> slocs = new ArrayList<Location>();
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
			
			String fs = it.next();
			
			Map<String,List<String>> map = maps.get(fs);
			Set<String> fset = map.keySet();
			Iterator<String> fit = fset.iterator();
			while(fit.hasNext()){
				
				String ss = fit.next();
				
				List<String> slist = map.get(ss);
				Iterator<String> sit = slist.iterator();
				while(sit.hasNext()){
					
					String ts = sit.next();
					System.out.println(fs + "-" + ss + "-" + ts);
				}
			}
		}
	}
}
