package com.chinahanjiang.crm.service;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinahanjiang.crm.pojo.ProductCatalog;
import com.chinahanjiang.crm.service.impl.ProductCatalogServiceImpl;

public class ProductCatalogServiceTest {

	private ApplicationContext context;

	@Before
	public void runContext() {

		context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
	}
	
	@Test
	public void testSearch() throws IOException{
		
		ProductCatalogService cs = (ProductCatalogServiceImpl) context
				.getBean("productCatalogService");
		
		ProductCatalog pc = cs.findProductCatalogByCode("HJP");
	}
}
