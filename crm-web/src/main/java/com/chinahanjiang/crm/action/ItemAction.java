package com.chinahanjiang.crm.action;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
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
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.service.ItemAttachmentService;
import com.chinahanjiang.crm.service.ItemService;
import com.chinahanjiang.crm.service.TaskService;
import com.chinahanjiang.crm.util.Constant;
import com.chinahanjiang.crm.util.DateUtil;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/item")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name="list",type="json"),
	@Result(name="dalyitem",type="json"),
	@Result(name="undoitem",type="json"),
	@Result(name="add",type="json"),
	@Result(name="modify",type="json"),
	@Result(name="delete",type="json"),
	@Result(name="check",type="json"),
	@Result(name="checkstatus",type="json"),
	@Result(name="addquote",type="json"),
	@Result(name="finish",type="json"),
	@Result(name="saveattachments",type="json")})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class ItemAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ItemService itemService;
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private ItemAttachmentService itemAttachmentService;
	
	private MessageDto md;
	
	private ItemDto id;
	
	private TaskDto td;
	
	private List<Object> rows;
	
	private int total;
	
	private int page;

	private String sort;

	private String order;

	public TaskDto getTd() {
		return td;
	}

	public void setTd(TaskDto td) {
		this.td = td;
	}

	public ItemDto getId() {
		return id;
	}

	public void setId(ItemDto id) {
		this.id = id;
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
	
	@Action("list")
	public String list(){
		
		int row = Integer
				.parseInt(this.httpServletRequest.getParameter("rows") == null ? "10"
						: this.httpServletRequest.getParameter("rows"));
		
		SearchResultDto srd = new SearchResultDto();
		
		srd = itemService.searchAndCount(this.order, this.sort,
				this.page, row, null, null, -1,  td);
		
		this.rows.clear();
		this.rows.addAll(srd.getRows());
		this.total = srd.getTotal();
		
		return "list";
	}
	
	@Action("dalyitem")
	public String dalyItem(){
		
		int row = Integer
				.parseInt(this.httpServletRequest.getParameter("rows") == null ? "10"
						: this.httpServletRequest.getParameter("rows"));
		
		SearchResultDto srd = new SearchResultDto();
		
		Timestamp begin = DateUtil.getCurrentDayStartTime();
		Timestamp end = DateUtil.getCurrentDayEndTime();
		
		srd = itemService.searchAndCount(this.order, this.sort,
				this.page, row, begin, end, 0, null);
		
		this.rows.clear();
		this.rows.addAll(srd.getRows());
		this.total = srd.getTotal();
		
		return "dalyitem";
	}
	
	@Action("undoitem")
	public String undoItem(){
		
		int row = Integer
				.parseInt(this.httpServletRequest.getParameter("rows") == null ? "10"
						: this.httpServletRequest.getParameter("rows"));
		
		SearchResultDto srd = new SearchResultDto();
		
		srd = itemService.searchAndCount(this.order, this.sort,
				this.page, row, null, null,0, null);
		
		this.rows.clear();
		this.rows.addAll(srd.getRows());
		this.total = srd.getTotal();
		
		
		return "undoitem";
	}
	
	@Action("add")
	public String add(){
		
		UserDto ud = (UserDto) this.session.get(Constant.USERKEY);
		md = itemService.update(id,td,ud);
		
		int tid = md.getIntF();
		String root = ServletActionContext.getServletContext().getRealPath("/uploadfile");
		File file =new File(root + "\\" + td.getId() + "\\" + tid ); 
		
		if(!file .exists()  && !file .isDirectory()){
			file .mkdir();
		}
		
		return "add";
	}
	
	@Action("modify")
	public String modify(){
		
		md = itemService.update(id,td,null);
		
		return "modify";
	}
	
	@Action("del")
	public String delete(){
		
		md = itemService.delete(id);
		
		return "delete";
	}
	
	@Action("checkstatus")
	public String checkStatus(){
		
		md = itemService.checkStatus(td);
		
		return "checkstatus";
	}
	
	@Action("finish")
	public String finish(){
		
		md = itemService.finishItem(id);
		
		return "finish";
	}
	
	@Action("addquote")
	public String addQuote(){
		
		UserDto ud = (UserDto) this.session.get(Constant.USERKEY);
		md = itemService.addQuoteItem(td,ud);
		
		return "addquote";
	}
	
	@Action("saveattttachments")
	public String saveAttachements(){
		
		UserDto ud = (UserDto) this.session.get(Constant.USERKEY);
		md = itemAttachmentService.save(id,ud);
		
		return "saveattachments";
	}
}
