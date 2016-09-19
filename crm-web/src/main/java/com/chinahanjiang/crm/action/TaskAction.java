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

import com.chinahanjiang.crm.dto.ItemDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.TaskDto;
import com.chinahanjiang.crm.dto.TaskTypeDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.service.TaskService;
import com.chinahanjiang.crm.util.Constant;
import com.chinahanjiang.crm.util.DateUtil;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/task")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name="list",type="json"),
	@Result(name="add",type="json"),
	@Result(name="modify",type="json"),
	@Result(name="delete",type="json"),
	@Result(name="check",type="json"),
	@Result(name="dalytask",type="json"),
	@Result(name="undotask",type="json"),
	@Result(name="generatecode",type="json"),
	@Result(name="finish",type="json"),
	@Result(name="giveup",type="json")})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class TaskAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private TaskService taskService;
	
	private TaskDto td;
	
	private ItemDto id;
	
	private MessageDto md;
	
	private List<Object> rows;
	
	private int total;
	
	private int page;

	private String sort;

	private String order;
	
	private String code;
	
	private TaskTypeDto ttd;
	
	private int tid;
	
	private String beginTime;
	
	private String endTime;
	
	private int tasktypeId;
	
	private int status;
	
	private int type;
	
	private String name;
	
	private String customerName;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getTasktypeId() {
		return tasktypeId;
	}

	public void setTasktypeId(int tasktypeId) {
		this.tasktypeId = tasktypeId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public ItemDto getId() {
		return id;
	}

	public void setId(ItemDto id) {
		this.id = id;
	}

	public TaskDto getTd() {
		return td;
	}

	public void setTd(TaskDto td) {
		this.td = td;
	}

	public MessageDto getMd() {
		return md;
	}

	public void setMd(MessageDto md) {
		this.md = md;
	}

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public TaskTypeDto getTtd() {
		return ttd;
	}

	public void setTtd(TaskTypeDto ttd) {
		this.ttd = ttd;
	}

	@Action("list")
	public String list(){
		
		int row = Integer
				.parseInt(this.httpServletRequest.getParameter("rows") == null ? "10"
						: this.httpServletRequest.getParameter("rows"));
		
		
		SearchResultDto srd = new SearchResultDto();
		
		if(type!=0){
			
			if(type==1){
				
				srd = taskService.searchAndCount(this.order, this.sort,
						this.page, row, beginTime, endTime , status, 0, tasktypeId, null, null);
				
			} else if(type==2) {
				
				srd = taskService.searchAndCount(this.order, this.sort,
						this.page, row, null, null , -1, 0, 0, name, null);
			} else if(type==3){
				
				srd = taskService.searchAndCount(this.order, this.sort,
						this.page, row, null, null , -1, 0, 0, null, customerName);
			}
		} else {
			
			srd = taskService.searchAndCount(this.order, this.sort,
					this.page, row);
		}
		
		
		
		this.rows.clear();
		this.rows.addAll(srd.getRows());
		this.total = srd.getTotal();
		
		return "list";
	}
	
	@Action("dalytask")
	public String dalyTask(){
		
		int row = Integer
				.parseInt(this.httpServletRequest.getParameter("rows") == null ? "10"
						: this.httpServletRequest.getParameter("rows"));
		
		
		SearchResultDto srd = new SearchResultDto();
		
		String todayBegin = DateUtil.getCurrentDayStr();
		String todayEnd = DateUtil.getCurrentDayStr();
		
		System.out.println(todayBegin + "-" + todayEnd);
		
		srd = taskService.searchAndCount(this.order, this.sort,
				this.page, row, todayBegin, todayEnd , -1, 0, 0, null, null);
		
		this.rows.clear();
		this.rows.addAll(srd.getRows());
		this.total = srd.getTotal();
		
		return "dalytask";
	}
	
	@Action("undotask")
	public String undoTask(){
		
		int row = Integer
				.parseInt(this.httpServletRequest.getParameter("rows") == null ? "10"
						: this.httpServletRequest.getParameter("rows"));
		
		
		SearchResultDto srd = new SearchResultDto();
		
		srd = taskService.searchAndCount(this.order, this.sort,
				this.page, row, null, null, 0, 0, 0, null, null);
		
		this.rows.clear();
		this.rows.addAll(srd.getRows());
		this.total = srd.getTotal();
		
		
		return "undotask";
	}
	
	@Action("add")
	public String add(){
	
		UserDto u = (UserDto) this.session.get(Constant.USERKEY);
		md = taskService.update(td,u);
				
		return "add";
	}
	
	@Action("modify")
	public String modify(){
		
		md = taskService.update(td, null);
		
		return "modify";
	}
	
	@Action("del")
	public String delete(){
		
		md = taskService.delete(td);
		
		return "delete";
	}
	
	@Action("generatecode")
	public String generateCode(){
		
		code = taskService.generateCode(ttd);
		return "generatecode";
	}
	
	@Action("finish")
	public String finish(){
		
		md = taskService.finishTask(td);
		
		return "finish";
	}
	
	@Action("giveup")
	public String giveup(){
		
		md = taskService.giveupTask(td);
		
		return "giveup";
	}
}
