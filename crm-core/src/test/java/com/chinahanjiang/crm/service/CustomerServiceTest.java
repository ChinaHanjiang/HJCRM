package com.chinahanjiang.crm.service;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinahanjiang.crm.dto.CustomerDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.service.impl.CustomerServiceImpl;

public class CustomerServiceTest {

	private ApplicationContext context;

	@Before
	public void runContext() {

		context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
	}
	
	@Test
	public void testSearch() throws IOException{
		
		CustomerService cs = (CustomerServiceImpl) context
				.getBean("customerService");
		
		CustomerDto cd = new CustomerDto();
		cd.setName("奥瑞金");
	}
}
