package com.chinahanjiang.crm.action;

import java.util.ArrayList;
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

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductQuoteDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.service.ProductQuoteService;
import com.chinahanjiang.crm.util.Constant;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/quote")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name="list",type="json"),
	@Result(name="add",type="json"),
	@Result(name="close",type="json"),
	@Result(name="find",type="json")
	})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class ProductQuoteAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ProductQuoteService productQuoteService;
	
	private List<Object> rows;

	private int total;

	private int page;

	private String sort;

	private String order;
	
	private int itemId;
	
	private int taskId;
	
	private MessageDto md;
	
	private ProductQuoteDto pqd;
	
	public ProductQuoteDto getPqd() {
		return pqd;
	}

	public void setPqd(ProductQuoteDto pqd) {
		this.pqd = pqd;
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

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public MessageDto getMd() {
		return md;
	}

	public void setMd(MessageDto md) {
		this.md = md;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	@Action("list")
	public String list(){
		
		int row = Integer
				.parseInt(this.httpServletRequest.getParameter("rows") == null ? "10"
						: this.httpServletRequest.getParameter("rows"));
		
		SearchResultDto srd = new SearchResultDto();
		
		//根据customerId查找contact
		srd = productQuoteService.searchAndCount(this.taskId, this.order, this.sort,
				this.page, row);
		
		if(this.rows == null) {
			this.rows=new ArrayList<Object>();
		}
		
		this.rows.clear();
		this.rows.addAll(srd.getRows());
		this.total = srd.getTotal();
		
		return "list";
	}
	
	@Action("add")
	public String add(){
		
		UserDto ud = (UserDto) this.session.get(Constant.USERKEY);
		md = productQuoteService.add(itemId,ud);
		
		return "add";
	}
	
	@Action("find")
	public String find(){
		
		md = productQuoteService.findProductQuoteByItemId(itemId);
		
		return "find";
	}
	
	
	@Action("close")
	public String close(){
		
		md = productQuoteService.closeProductQuote(pqd);
		
		return "close";
	}
}
