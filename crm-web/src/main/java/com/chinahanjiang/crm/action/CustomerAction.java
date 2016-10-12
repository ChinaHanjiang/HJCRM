package com.chinahanjiang.crm.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.chinahanjiang.crm.dto.CustomerDto;
import com.chinahanjiang.crm.dto.DataListDto;
import com.chinahanjiang.crm.dto.LocationDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.service.CustomerService;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/customer")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name="list",type="json"),
	@Result(name="add",type="json"),
	@Result(name="modify",type="json"),
	@Result(name="del",type="json"),
	@Result(name="search",type="json"),
	@Result(name="generatecode",type="json")})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CustomerAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Resource
	private CustomerService customerService;
	
	private List<Object> rows;
	
	private int total;
	
	private int page;

	private String sort;

	private String order;
	
	private String code;
	 
	private int locId;
	
	private CustomerDto cd;
	
	private LocationDto ld;
	
	private MessageDto md;
	
	private String q;
	
	public MessageDto getMd() {
		return md;
	}

	public void setMd(MessageDto md) {
		this.md = md;
	}

	public CustomerDto getCd() {
		return cd;
	}

	public void setCd(CustomerDto cd) {
		this.cd = cd;
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

	public int getLocId() {
		return locId;
	}

	public void setLocId(int locId) {
		this.locId = locId;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocationDto getLd() {
		return ld;
	}

	public void setLd(LocationDto ld) {
		this.ld = ld;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	@Action("list")
	public String list(){
		
		int row = Integer
				.parseInt(this.httpServletRequest.getParameter("rows") == null ? "10"
						: this.httpServletRequest.getParameter("rows"));
		
		
		SearchResultDto srd = new SearchResultDto();
		
		srd = customerService.searchAndCount(this.locId, this.order, this.sort,
				this.page, row);
		
		this.rows.clear();
		this.rows.addAll(srd.getRows());
		this.total = srd.getTotal();
		
		return "list";
	}
	
	@Action("add")
	public String add(){
		
		md = customerService.update(cd);
		
		return "add";
	}
	
	@Action("modify")
	public String modify(){
		
		md = customerService.update(cd);
		
		return "modify";
	}
	
	@Action("del")
	public String del(){
		
		md = customerService.delete(cd);
		
		return "del";
	}
	
	@Action("search")
	public String search(){
		
		List<DataListDto> dld = customerService.search(cd);
		
		this.rows = new ArrayList<Object>();
		
		this.rows.clear();
		this.rows.addAll(dld);
		this.total = dld.size();
		
		return "search";
	}
	
	@Action("generatecode")
	public String generateCode(){
		
		code = customerService.generateCode(ld);
		return "generatecode";
	}
	
	@Action("find")
	public void find() throws IOException{
		
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html");  
		PrintWriter out;  
		out = response.getWriter();
		String treeStr =  customerService.searchByName(q.trim());
		out.println(treeStr);  
		out.flush();  
		out.close();  
	}
}
