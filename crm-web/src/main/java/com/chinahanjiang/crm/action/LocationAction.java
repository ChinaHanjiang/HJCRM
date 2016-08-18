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

import com.chinahanjiang.crm.service.LocationService;

@Controller
@ParentPackage("json-default")
@Namespace("/loc")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name="list",type="json")})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class LocationAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private LocationService locationService;
	
	private String locTree;
	
	public String getLocTree() {
		return locTree;
	}

	public void setLocTree(String locTree) {
		this.locTree = locTree;
	}

	@Action("list")
	public String list(){
		locTree =  "[" + locationService.getAllLocations() + "]";
		return "list";
	}
}
