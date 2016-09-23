package com.chinahanjiang.crm.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.pojo.Groups;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.ItemType;
import com.chinahanjiang.crm.pojo.Location;
import com.chinahanjiang.crm.pojo.Product;
import com.chinahanjiang.crm.pojo.ProductAndQuoteRelation;
import com.chinahanjiang.crm.pojo.ProductQuoteDetails;
import com.chinahanjiang.crm.pojo.Task;
import com.chinahanjiang.crm.pojo.TaskType;
import com.chinahanjiang.crm.pojo.Unit;
import com.chinahanjiang.crm.service.CustomerService;
import com.chinahanjiang.crm.service.GroupsService;
import com.chinahanjiang.crm.service.ItemService;
import com.chinahanjiang.crm.service.ItemTypeService;
import com.chinahanjiang.crm.service.LocationService;
import com.chinahanjiang.crm.service.ProductAndQuoteRelationService;
import com.chinahanjiang.crm.service.ProductQuoteService;
import com.chinahanjiang.crm.service.ProductService;
import com.chinahanjiang.crm.service.TaskService;
import com.chinahanjiang.crm.service.TaskTypeService;
import com.chinahanjiang.crm.service.UnitService;

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
	@Result(name = "addtask", location = "/WEB-INF/content/task/taskedit.jsp"),
	@Result(name = "modifytask", location = "/WEB-INF/content/task/taskedit.jsp"),
	@Result(name = "additem", location = "/WEB-INF/content/task/itemedit.jsp"),
	@Result(name = "modifyitem", location = "/WEB-INF/content/task/itemedit.jsp"),
	@Result(name = "itemlist", location = "/WEB-INF/content/task/itemlist.jsp"),
	@Result(name = "quote", location = "/WEB-INF/content/quote/quotewindow.jsp"),
	@Result(name = "taskdetail", location = "/WEB-INF/content/task/taskdetail.jsp"),
	@Result(name = "itemdetail", location = "/WEB-INF/content/task/itemdetail.jsp"),
	@Result(name = "addproduct", location = "/WEB-INF/content/product/productedit.jsp"),
	@Result(name = "modifyproduct", location = "/WEB-INF/content/product/productedit.jsp"),
	@Result(name = "productdetail", location = "/WEB-INF/content/product/productdetails.jsp")})
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
	
	@Resource
	private ProductAndQuoteRelationService paqrService;
	
	@Resource
	private ProductQuoteService productQuoteServices;
	
	@Resource
	private UnitService unitService;
	
	private List<Location> locations;
	
	private List<Groups> groups;
	
	private List<TaskType> taskTypes;
	
	private List<ItemType> itemTypes;
	
	private List<Customer> customers;
	
	private List<Product> products;
	
	private List<ProductQuoteDetails> pqds;
	
	private List<ProductAndQuoteRelation> paqrs;
	
	private List<Unit> units;
	
	private Item item;
	
	private int taskId;
	
	private Task task;

	private String itemCode;
	
	private int itemId;
	
	private int quoteId;
	
	private int type; //1-add,2-modify
	
	private int productId;
	
	private Product product;
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

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

	public List<ProductAndQuoteRelation> getPaqrs() {
		return paqrs;
	}

	public void setPaqrs(List<ProductAndQuoteRelation> paqrs) {
		this.paqrs = paqrs;
	}

	public List<ProductQuoteDetails> getPqds() {
		return pqds;
	}

	public void setPqds(List<ProductQuoteDetails> pqds) {
		this.pqds = pqds;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
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
		
		this.type = 1;
		taskTypes = taskTypeService.findAll();
		
		return "addtask";
	}
	
	@Action("modifytask")
	public String mofidytask(){
		
		this.type = 2;
		task = taskService.findById(taskId);
		taskTypes = taskTypeService.findAll();
		
		return "modifytask";
	}
	
	@Action("additem")
	public String additem(){
		
		this.type = 1;
		task = taskService.findById(taskId);
		itemTypes = itemTypeService.findAll();
		int itemnum = task.getItems()==null?1:task.getItems().size()+1 ;
		itemCode = task.getCode() + "." + itemnum;
		return "additem";
	}
	
	@Action("modifyitem")
	public String modifyitem(){
		
		this.type = 2;
		item = itemService.findById(itemId);
		task = item.getTask();
		itemTypes = itemTypeService.findAll();
		itemCode = item.getCode();
		return "modifyitem";
	}
	
	@Action("itemlist")
	public String itemlist(){
		
		return "itemlist";
	}
	
	@Action("quote")
	public String quote(){
		
		item = itemService.findById(itemId);
		task = item.getTask();
		pqds = productQuoteServices.findProductQuoteDetailsByItem(item);
		return "quote";
	}
	
	@Action("taskdetail")
	public String taskDetail(){
		
		task = taskService.findById(taskId);
		products = productService.findByTask(task);
		
		return "taskdetail";
	}
	
	@Action("itemdetail")
	public String upload(){
		
		item = itemService.findById(itemId);
		int flag = item.getFlag();
		if(flag == 1){
			
			pqds = productQuoteServices.findProductQuoteDetailsByItem(item);
		}
		
		return "itemdetail";
	}
	
	@Action("addproduct")
	public String addproduct(){
		
		this.type = 1;
		units = unitService.findAllUnits();
		
		return "addproduct";
	}
	
	@Action("modifyproduct")
	public String modifyProduct(){
		
		this.type = 2;
		product = productService.findById(productId);
		System.out.println(product);
		units = unitService.findAllUnits();
		
		return "modifyproduct";
	}
	
	@Action("productdetail")
	public String productDetail(){
		
		product = productService.findById(productId);
		return "productdetail";
	}
}
