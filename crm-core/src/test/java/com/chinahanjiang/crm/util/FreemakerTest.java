package com.chinahanjiang.crm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class FreemakerTest {

	@Test
	public void testDeneratreDoc(){
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		dataMap.put("qtitle", "111111");
		dataMap.put("qdate", "2016-9-22");
		dataMap.put("qcode", "12016093");
		
		dataMap.put("brieftitle", "简要介绍");
		int index = 1;
		List<Map<String,Object>> brief = new ArrayList<Map<String,Object>>();
		for(int i=0;i<3;i++){
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("content", "content" + index);
			brief.add(map);
			index ++;
		}
		
		dataMap.put("brieftb", brief);
		
		List<Map<String,Object>> space = new ArrayList<Map<String,Object>>();
		for(int i=0;i<5;i++){
			
			Map<String,Object> map = new HashMap<String, Object>();
			space.add(map);
			
		}
		
		dataMap.put("spacetb", space);
		
		List<Map<String,Object>> quote = new ArrayList<Map<String,Object>>();
		for(int i=0;i<3;i++){
			
			Map<String,Object> qmap = new HashMap<String, Object>();
			
			List<Map<String,Object>> titles = new ArrayList<Map<String,Object>>();
			for(int j=0;j<3;j++){
				
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("ename", "Name" + j);
				map.put("cname", "名字" + j);
				map.put("index", j+1);
				
				titles.add(map);
			}
			
			qmap.put("titles", titles);
			qmap.put("titlenum", titles.size());
			
			List<Map<String,Object>> contents = new ArrayList<Map<String,Object>>();
			for(int k=0;k<5;k++){
				
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("order", k+1);
				map.put("cname", "产品" + k);
				map.put("ename", "product" + k);
				map.put("quantity", k+1);
				map.put("standardprice", 1000*k);
				map.put("defindprice", 2000*k);
				
				contents.add(map);
			}
			
			qmap.put("contents", contents);
			
			qmap.put("totalprice", 10000);
			
			quote.add(qmap);
		}
		
		dataMap.put("quotetb", quote);
		
		DocumentHandler dh = new DocumentHandler();
		dh.craeteDoc(dataMap, "D:\\123.doc");
	}
}
