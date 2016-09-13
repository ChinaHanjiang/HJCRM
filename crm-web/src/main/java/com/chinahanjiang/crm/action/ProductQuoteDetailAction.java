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
import com.chinahanjiang.crm.dto.ProductConfigurationDto;
import com.chinahanjiang.crm.service.ProductQuoteDetailsService;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/quotedetail")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name="list",type="json"),
	@Result(name="savemix",type="json")
	})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class ProductQuoteDetailAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	@Resource
	private ProductQuoteDetailsService productQuoteDetailsService;
	
	private List<Object> rows;

	private int total;

	private int page;

	private String sort;

	private String order;
	
	private int itemId;
	
	private int productId;
	
	private String inserted;
	
	private String updated;
	
	private String deleted;
	
	private int productQuoteDetailId;
	
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

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getInserted() {
		return inserted;
	}

	public void setInserted(String inserted) {
		this.inserted = inserted;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	public int getProductQuoteDetailId() {
		return productQuoteDetailId;
	}

	public void setProductQuoteDetailId(int productQuoteDetailId) {
		this.productQuoteDetailId = productQuoteDetailId;
	}

	@Action("list")
	public String list(){
		
		System.out.println("1111");
		
		List<ProductConfigurationDto> cds = productQuoteDetailsService
				.loadProductMixs(productQuoteDetailId);
		
		this.rows = new ArrayList<Object>();
		this.rows.clear();
		this.rows.addAll(cds);
		this.total = cds.size();
		
		return "list";
	}
	
	@Action("savemix")
	public String savemix(){
		
		System.out.println("itemId:" + this.itemId);
		System.out.println("productId:" + this.productId);
		System.out.println("inserted:" + this.inserted);
		System.out.println("updated:" + this.updated);
		System.out.println("deleted:" + this.deleted);
		
		md = productQuoteDetailsService.saveProductMixForQuote(this.itemId,this.productId,
				this.inserted, this.updated, this.deleted);

		return "savemix";
	}
	
}
