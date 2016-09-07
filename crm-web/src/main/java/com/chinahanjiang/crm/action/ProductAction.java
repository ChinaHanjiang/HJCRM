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
import com.chinahanjiang.crm.dto.ProductDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.service.ProductService;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/product")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name="list",type="json"),
	@Result(name="add",type="json"),
	@Result(name="modify",type="json"),
	@Result(name="delete",type="json"),
	@Result(name="load",type="json")
	})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class ProductAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Resource
	private ProductService productService;
	
	private List<Object> rows;
	
	private int total;
	
	private int page;
	
	private String sort;
	
	private String order;
	
	private int productCatalogId;
	
	private MessageDto md;
	
	private ProductDto pd;
	
	private int catalogId;
	
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

	public int getProductCatalogId() {
		return productCatalogId;
	}

	public void setProductCatalogId(int productCatalogId) {
		this.productCatalogId = productCatalogId;
	}
	
	public MessageDto getMd() {
		return md;
	}

	public void setMd(MessageDto md) {
		this.md = md;
	}

	public ProductDto getPd() {
		return pd;
	}

	public void setPd(ProductDto pd) {
		this.pd = pd;
	}
	
	public int getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}

	@Action("list")
	public String list(){
		
		int row = Integer
				.parseInt(this.httpServletRequest.getParameter("rows") == null ? "10"
						: this.httpServletRequest.getParameter("rows"));
		
		
		SearchResultDto srd = new SearchResultDto();
		
		srd = productService.searchAndCount(this.productCatalogId, this.order, this.sort,
				this.page, row);
		
		this.rows.clear();
		this.rows.addAll(srd.getRows());
		this.total = srd.getTotal();
		
		return "list";
	}
	
	@Action("add")
	public String add(){
		
		md = productService.update(pd);
		
		return "add";
	}
	
	@Action("modify")
	public String modify(){
		
		md = productService.update(pd);
		
		return "modify";
	}
	
	@Action("del")
	public String delete(){
		
		md = productService.delete(pd);
		
		return "delete";
	}
	
	@Action("find")
	public void find() throws IOException{
		
		HttpServletResponse response=ServletActionContext.getResponse();
		 response.setContentType("text/html");  
	     PrintWriter out;  
	     out = response.getWriter();
	     String treeStr =  productService.getProductsByCatalogId(catalogId);
	     out.println(treeStr);  
	     out.flush();  
	     out.close();  
	}
	
	@Action("load")
	public String load(){
		
		pd = productService.findById(pd);
		return "load";
	}
}
