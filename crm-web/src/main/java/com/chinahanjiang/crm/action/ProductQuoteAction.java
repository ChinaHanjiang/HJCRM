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
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.service.ProductQuoteService;
import com.chinahanjiang.crm.util.Constant;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/quote")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name="list",type="json"),
	@Result(name="add",type="json")
	})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class ProductQuoteAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ProductQuoteService productQuoteService;
	
	private int itemId;
	
	private MessageDto md;
	
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
	
	@Action("add")
	public String add(){
		
		UserDto ud = (UserDto) this.session.get(Constant.USERKEY);
		md = productQuoteService.add(itemId,ud);
		
		return "add";
	}
	
}
