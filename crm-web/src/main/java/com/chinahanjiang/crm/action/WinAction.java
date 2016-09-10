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
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.ItemType;
import com.chinahanjiang.crm.pojo.Location;
import com.chinahanjiang.crm.pojo.Product;
import com.chinahanjiang.crm.pojo.Task;
import com.chinahanjiang.crm.pojo.TaskType;
import com.chinahanjiang.crm.service.CustomerService;
import com.chinahanjiang.crm.service.GroupsService;
import com.chinahanjiang.crm.service.ItemService;
import com.chinahanjiang.crm.service.ItemTypeService;
import com.chinahanjiang.crm.service.LocationService;
import com.chinahanjiang.crm.service.ProductService;
import com.chinahanjiang.crm.service.TaskService;
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
	@Result(name = "userlist", location = "/WEB-INF/content/setting/userlist.jsp"),
	@Result(name = "quotelist", location = "/WEB-INF/content/quote/quotelist.jsp"),
	@Result(name = "productlist", location = "/WEB-INF/content/product/productlist.jsp"),
	@Result(name = "quotewindow", location = "/WEB-INF/content/quote/quotewindow.jsp"),
	@Result(name = "addtask", location = "/WEB-INF/content/task/addtask.jsp"),
	@Result(name = "additem", location = "/WEB-INF/content/task/additem.jsp"),
	@Result(name = "itemlist", location = "/WEB-INF/content/task/itemlist.jsp"),
	@Result(name = "quote", location = "/WEB-INF/content/quote/quotewindow.jsp")})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class WinAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Resource
	private LocationService locService;
	
	@Resource
	private GroupsService groupsService;
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private TaskTypeService taskTypeService;
	
	@Resource
	private CustomerService customerService;
	
	@Resource
	private ItemTypeService itemTypeService;
	
	@Resource
	private ItemService itemService;
	
	@Resource
	private ProductService productService;
	
	private List<Location> locations;
	
	private List<Groups> groups;
	
	private List<TaskType> taskTypes;
	
	private List<ItemType> itemTypes;
	
	private List<Customer> customers;
	
	private List<Product> products;
	
	private Item item;
	
	private int taskId;
	
	private Task task;

	private String itemCode;
	
	private int itemId;
	
	private int quoteId;
	
	public int getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(int quoteId) {
		this.quoteId = quoteId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

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
	
	public List<ItemType> getItemTypes() {
		return itemTypes;
	}

	public void setItemTypes(List<ItemType> itemTypes) {
		this.itemTypes = itemTypes;
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
		
		return "tasklist";
	}

	@Action("userlist")
	public String useristWin(){
		
		return "userlist";
	}
	
	@Action("quotelist")
	public String quotelistWin(){
		
		return "quotelist";
	}
	
	@Action("quotewindow")
	public String quoteWindow(){
		
		return "quotewindow";
	}
	
	@Action("productlist")
	public String productlistWin(){
		
		return "productlist";
	}
	
	@Action("addtask")
	public String addtask(){
		
		taskTypes = taskTypeService.findAll();
		
		return "addtask";
	}
	
	@Action("additem")
	public String additem(){
		
		task = taskService.findById(taskId);
		itemTypes = itemTypeService.findAll();
		int itemnum = task.getItems()==null?1:task.getItems().size()+1 ;
		itemCode = task.getCode() + "." + itemnum;
		return "additem";
	}
	
	@Action("itemlist")
	public String itemlist(){
		
		return "itemlist";
	}
	
	@Action("quote")
	public String quote(){
		
		item = itemService.findById(itemId);
		task = item.getTask();
		products = productService.findByTask(task);
		
		return "quote";
	}
}
