package com.chinahanjiang.crm.action;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductCatalogDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.service.ProductCatalogService;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/catalog")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name="gridlist",type="json"),
	@Result(name="list",type="json"),
	@Result(name="add",type="json"),
	@Result(name="modify",type="json"),
	@Result(name="delete",type="json"),
	@Result(name="check",type="json")})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class ProductCatalogAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ProductCatalogService productCatalogService;
	
	private List<Object> rows;

	private int total;

	private int page;

	private String sort;

	private String order;
	
	private String pcTree;
	
	private ProductCatalogDto pcd;
	
	private MessageDto md;
	
	private int id;
	
	private int parentCatalogId;
	
	public int getParentCatalogId() {
		return parentCatalogId;
	}

	public void setParentCatalogId(int parentCatalogId) {
		this.parentCatalogId = parentCatalogId;
	}

	public MessageDto getMd() {
		return md;
	}

	public void setMd(MessageDto md) {
		this.md = md;
	}

	public ProductCatalogDto getPcd() {
		return pcd;
	}

	public void setPcd(ProductCatalogDto pcd) {
		this.pcd = pcd;
	}

	public String getPcTree() {
		return pcTree;
	}

	public void setPcTree(String pcTree) {
		this.pcTree = pcTree;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Action("gridlist")
	public String gridlist(){
		
		int row = Integer
				.parseInt(this.httpServletRequest.getParameter("rows") == null ? "10"
						: this.httpServletRequest.getParameter("rows"));
		
		
		SearchResultDto srd = new SearchResultDto();
		
		srd = productCatalogService.searchAndCount(this.parentCatalogId,this.order, this.sort,
				this.page, row);
		
		this.rows.clear();
		this.rows.addAll(srd.getRows());
		this.total = srd.getTotal();
		return "gridlist";
	}
	
	@Action("list")
	public String list(){
		
		pcTree =  "[" + productCatalogService.getAllCatalogs() + "]";
		return "list";
	}
	
	@Action("add")
	public String add(){
		
		md = productCatalogService.update(pcd);
		
		return "add";
	}
	
	@Action("modify")
	public String modify(){
		
		md = productCatalogService.update(pcd);
		
		return "modify";
	}
	
	@Action("del")
	public String delete(){
		
		md = productCatalogService.delete(pcd);
		
		return "delete";
	}
	
	@Action("check")
	public String check(){
		
		md = productCatalogService.check(pcd);
		
		return "check";
	}
	
	@Action("tree")
	public void tree() throws IOException{
		
		 HttpServletResponse response=ServletActionContext.getResponse();
		 response.setContentType("text/html");  
	     PrintWriter out;  
	     out = response.getWriter();
	     String treeStr =  productCatalogService.getCatalogsByParentId(id);
	     out.println(treeStr);  
	     out.flush();  
	     out.close();  
	}
}
