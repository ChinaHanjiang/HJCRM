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

import com.chinahanjiang.crm.dto.ContactDto;
import com.chinahanjiang.crm.dto.DataListDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.service.ContactService;

@Controller
@ParentPackage("json-default")
@Namespace("/contact")
@Results({ @Result(name = "error", location = "/error.jsp"),
		@Result(name = "list", type = "json"),
		@Result(name = "add", type = "json"),
		@Result(name = "modify", type = "json"),
		@Result(name = "del", type = "json"),
		@Result(name = "find", type = "json")})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class ContactAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ContactService contactService;
	
	private List<Object> rows;

	private int total;

	private int page;

	private String sort;

	private String order;
	
	private int customerId;
	
	private ContactDto cd;
	
	private MessageDto md;
	
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

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public MessageDto getMd() {
		return md;
	}

	public void setMd(MessageDto md) {
		this.md = md;
	}
	
	public ContactDto getCd() {
		return cd;
	}

	public void setCd(ContactDto cd) {
		this.cd = cd;
	}

	@Action("list")
	public String list(){
		
		int row = Integer
				.parseInt(this.httpServletRequest.getParameter("rows") == null ? "10"
						: this.httpServletRequest.getParameter("rows"));
		
		SearchResultDto srd = new SearchResultDto();
		
		//根据customerId查找contact
		srd = contactService.searchAndCount(this.customerId, this.order, this.sort,
				this.page, row);
		
		this.rows.clear();
		this.rows.addAll(srd.getRows());
		this.total = srd.getTotal();
		
		return "list";
	}
	
	@Action("add")
	public String add(){
		
		md = contactService.update(cd);
		
		return "add";
	}
	
	@Action("modify")
	public String modify(){
		
		md = contactService.update(cd);
		
		return "modify";
	}
	
	@Action("del")
	public String del(){
		
		md = contactService.delete(cd);
		
		return "del";
	}
	
	@Action("find")
	public String find(){
		
		List<ContactDto> cds = contactService.search(cd);
		
		this.rows = new ArrayList<Object>();
		
		this.rows.clear();
		this.rows.addAll(cds);
		this.total = cds.size();
		
		return "find";
	}
}
