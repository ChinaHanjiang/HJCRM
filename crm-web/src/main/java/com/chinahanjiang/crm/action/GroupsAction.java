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

import com.chinahanjiang.crm.dto.GroupsDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.service.GroupsService;

@Controller
@ParentPackage("json-default")
@Namespace("/groups")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name="list",type="json"),
	@Result(name="add",type="json"),
	@Result(name="modify",type="json"),
	@Result(name="delete",type="json"),
	@Result(name="check",type="json")})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class GroupsAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private GroupsService groupsService;
	
	private List<Object> rows;
	
	private int total;
	
	private int page;

	private String sort;

	private String order;
	
	private GroupsDto gd;
	
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

	
	public GroupsDto getGd() {
		return gd;
	}

	public void setGd(GroupsDto gd) {
		this.gd = gd;
	}

	public MessageDto getMd() {
		return md;
	}

	public void setMd(MessageDto md) {
		this.md = md;
	}

	@Action("list")
	public String list(){
		
		int row = Integer
				.parseInt(this.httpServletRequest.getParameter("rows") == null ? "10"
						: this.httpServletRequest.getParameter("rows"));
		
		
		SearchResultDto srd = new SearchResultDto();
		
		srd = groupsService.searchAndCount(this.order, this.sort,
				this.page, row);
		
		this.rows.clear();
		this.rows.addAll(srd.getRows());
		this.total = srd.getTotal();
		
		return "list";
	}
	
	@Action("add")
	public String add(){
		
		md = groupsService.update(gd);
		return "add";
	}
	
	@Action("modify")
	public String modify(){
		
		md = groupsService.update(gd);
		
		return "modify";
	}
	
	@Action("del")
	public String delete(){
		
		md = groupsService.delete(gd);
		
		return "delete";
	}
	
	@Action("check")
	public String check(){
		
		md = groupsService.check(gd);
		
		return "check";
	}
}
