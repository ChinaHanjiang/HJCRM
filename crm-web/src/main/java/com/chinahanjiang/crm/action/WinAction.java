package com.chinahanjiang.crm.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

@Controller
@ParentPackage("struts-default")
@Namespace("/win")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name = "success", location = "/WEB-INF/content/main.jsp"),
	@Result(name = "customerlist", location = "/WEB-INF/content/customer/customerlist.jsp")})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class WinAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Action("customerlist")
	public String customerListWin(){
		
		return "customerlist";
	}

}
