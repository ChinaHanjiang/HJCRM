package com.chinahanjiang.crm.service;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.Task;
import com.chinahanjiang.crm.service.impl.TaskServiceImpl;
import com.chinahanjiang.crm.service.impl.ItemServiceImpl;

public class TaskServiceTest {

	private ApplicationContext context;

	@Before
	public void runContext() {

		context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
	}
	
	@Test
	public void testCreateFilePath(){
		
		TaskService ts = (TaskServiceImpl) context.getBean("taskService");
		ItemService is = (ItemServiceImpl) context.getBean("itemService");
		List<Task> tasks = ts.findAllTask();
		Iterator<Task> it = tasks.iterator();
		while(it.hasNext()){
			
			Task t = it.next();
			
			int id = t.getId();
			
			String root = "C:\\Users\\tree\\git\\hj-crm-project\\crm-web\\src\\main\\webapp\\uploadfile";
			File file =new File(root + "\\" + id ); 
			
			if(!file .exists()  && !file .isDirectory()){
				file .mkdir();
			}
			
			List<Item> items = is.findItemsByTask(t);
			Iterator<Item> iit = items.iterator();
			while(iit.hasNext()){
				
				Item i = iit.next();
				int iid = i.getId();
				
				File ifile =new File(root + "\\" + id + "\\" + iid ); 
				
				if(!ifile .exists()  && !ifile .isDirectory()){
					ifile .mkdir();
				}
			}
		}
	}
}
