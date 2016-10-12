package com.chinahanjiang.crm.util;

import java.util.List;

import org.junit.Test;

import com.chinahanjiang.crm.dto.ProductDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataUtilTest {

	@Test
	public void testConverNumToStr() {

		System.out.println(DataUtil.converNumToStr(56));
	}

	@Test
	public void testSplit(){
		
		String st = "\\20\\89\\CorelDRAW_X7_keygen.zip";
		String[] s = st.split("\\\\");
		System.out.println(s.length);
	}
	
	@Test
	public void testGson() {

		String insert = "[{'productCatalogId':7,'productId':5,'productCatalog':'缝焊机','name':'半自动缝焊机FH-3A'},{'productCatalogId':8,'productId':8,'productCatalog':'粉末喷涂机','name':'粉末喷涂机FM-5'}]";
		String delete = "[{'code':'','createTime':'2016-09-06 11:30:31','id':1,'mixNum':1,'name':'#668 四模二片罐生产线','orders':0,'parentCatalog':null,'productCatalog':'二片罐生产线','productCatalogId':4,'remarks':'111','shortCode':'#668M4','standardPrice':222,'updateTime':'2016-09-06 14:27:18','user':'','userId':0,'productId':1}]";
		
		Gson gs = new Gson();
		List<ProductDto> insertp = gs.fromJson(insert, new TypeToken<List<ProductDto>>(){}.getType());
		System.out.println(insertp.size());
		
		List<ProductDto> deletep = gs.fromJson(delete, new TypeToken<List<ProductDto>>(){}.getType());
		System.out.println(deletep.size());
		
	}
	
	@Test
	public void testDouble(){
		
		System.out.println(Double.toString(new Double(2.5)));
	}
}
