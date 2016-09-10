package com.chinahanjiang.crm.action;

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
	
	private int quoteId;
	
	private int productId;
	
	private String inserted;
	
	private String updated;
	
	private String deleted;
	
	private MessageDto md;

	public MessageDto getMd() {
		return md;
	}

	public void setMd(MessageDto md) {
		this.md = md;
	}

	public int getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(int quoteId) {
		this.quoteId = quoteId;
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
	
	@Action("savemix")
	public String savemix(){
		
		System.out.println("quoteId:" + this.quoteId);
		System.out.println("productId:" + this.productId);
		System.out.println("inserted:" + this.inserted);
		System.out.println("updated:" + this.updated);
		System.out.println("deleted:" + this.deleted);
		
		/*md = productQuoteDetailsService.saveProductMixForQuote(this.quoteId,this.productId,
				this.inserted, this.updated, this.deleted);
*/
		return "savemix";
	}
	
}
