package com.chinahanjiang.crm.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinahanjiang.crm.pojo.Product;
import com.chinahanjiang.crm.pojo.Task;
import com.chinahanjiang.crm.service.impl.ProductServiceImpl;
import com.chinahanjiang.crm.service.impl.TaskServiceImpl;

public class ProductServiceTest {

	private ApplicationContext context;

	@Before
	public void runContext() {

		context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
	}
	
	@Test
	public void testFindByTask(){
		
		ProductService cs = (ProductServiceImpl) context
				.getBean("productService");
		
		TaskService ts = (TaskServiceImpl) context.getBean("taskService");
		
		Task task = ts.findById(10);
		
		List<Product> products = cs.findByTask(task);
		System.out.println(products.size());
	}
}
