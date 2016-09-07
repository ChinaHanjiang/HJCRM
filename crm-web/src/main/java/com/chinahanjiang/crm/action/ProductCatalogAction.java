package com.chinahanjiang.crm.action;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.chinahanjiang.crm.service.ProductCatalogService;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/catalog")
@Results({ @Result(name = "error", location = "/error.jsp"),
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
	
	private String pcTree;
	
	private ProductCatalogDto pcd;
	
	private MessageDto md;
	
	private int id;
	
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
