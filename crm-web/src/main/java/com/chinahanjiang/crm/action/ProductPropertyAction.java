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
import com.chinahanjiang.crm.dto.ProductPropertyDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.service.ProductPropertyService;
import com.chinahanjiang.crm.util.Constant;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/productproperty")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name="list",type="json")
	})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class ProductPropertyAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ProductPropertyService productPropertyService;
	
	private List<Object> rows;

	private int total;

	private int page;

	private String sort;

	private String order;
	
	private MessageDto md;
	
	private ProductPropertyDto ppd;
	
	private int productId;

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

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public ProductPropertyDto getPpd() {
		return ppd;
	}

	public void setPpd(ProductPropertyDto ppd) {
		this.ppd = ppd;
	}
	
	public MessageDto getMd() {
		return md;
	}

	public void setMd(MessageDto md) {
		this.md = md;
	}

	@Action("list")
	public String list(){
		
		List<ProductPropertyDto> ppds = productPropertyService
				.loadProductProperties(productId);

		this.rows = new ArrayList<Object>();

		this.rows.clear();
		this.rows.addAll(ppds);
		this.total = ppds.size();

		return "list";
	}
	
	@Action("add")
	public String add(){
		
		UserDto ud = (UserDto) this.session.get(Constant.USERKEY);
		md = productPropertyService.update(ppd,ud);
		
		return "add";
	}
	
	@Action("modify")
	public String modify(){
		
		md = productPropertyService.update(ppd,null);
		
		return "modify";
	}
	
	@Action("delete")
	public String delete(){
		
		md = productPropertyService.delete(ppd);
		return "delete";
	}
	
}
