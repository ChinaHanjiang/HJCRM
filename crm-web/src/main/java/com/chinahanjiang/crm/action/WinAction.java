package com.chinahanjiang.crm.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.pojo.Groups;
import com.chinahanjiang.crm.pojo.Location;
import com.chinahanjiang.crm.pojo.TaskType;
import com.chinahanjiang.crm.service.CustomerService;
import com.chinahanjiang.crm.service.GroupsService;
import com.chinahanjiang.crm.service.LocationService;
import com.chinahanjiang.crm.service.TaskTypeService;

@Controller
@ParentPackage("default")
@Namespace("/win")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name = "success", location = "/WEB-INF/content/main.jsp"),
	@Result(name = "customerlist", location = "/WEB-INF/content/customer/customerlist.jsp"),
	@Result(name = "groupslist", location = "/WEB-INF/content/setting/groupslist.jsp"),
	@Result(name = "tasktypelist", location = "/WEB-INF/content/setting/tasktypelist.jsp"),
	@Result(name = "tasklist", location = "/WEB-INF/content/task/tasklist.jsp"),
	@Result(name = "userlist", location = "/WEB-INF/content/setting/userlist.jsp")})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class WinAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Resource
	private LocationService locService;
	
	@Resource
	private GroupsService groupsService;
	
	@Resource
	private TaskTypeService taskTypeService;
	
	@Resource
	private CustomerService customerService;
	
	private List<Location> locations;
	
	private List<Groups> groups;
	
	private List<TaskType> taskTypes;
	
	private List<Customer> customers;
	
	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
	
	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}
	
	public List<TaskType> getTaskTypes() {
		return taskTypes;
	}

	public void setTaskTypes(List<TaskType> taskTypes) {
		this.taskTypes = taskTypes;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	@Action("customerlist")
	public String customerListWin(){
		
		/*初始化一级区域信息*/
		locations = locService.loadLocationsByPid(1);
		
		/*加载集团信息*/
		groups = groupsService.loadGroups();
		
		return "customerlist";
	}
	
	@Action("groupslist")
	public String groupsListWin(){
		
		return "groupslist";
	}
	
	@Action("tasktypelist")
	public String taskTypeListWin(){
		
		return "tasktypelist";
	}
	
	@Action("tasklist")
	public String taskListWin(){
		
		taskTypes = taskTypeService.findAll();
		//customers = customerService.loadCustomers(20);
		
		return "tasklist";
	}

	@Action("userlist")
	public String useristWin(){
		
		return "userlist";
	}
}
