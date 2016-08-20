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
	@Result(name="list",type="json"),
	@Result(name="subloc",type="json"),
	@Result(name="parloc",type="json")})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class LocationAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private LocationService locService;
	
	private String locTree;
	
	private int pid;
	
	private int locId;
	
	private String subLocs;
	
	private String parentLocs;
	
	public String getLocTree() {
		return locTree;
	}

	public void setLocTree(String locTree) {
		this.locTree = locTree;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getLocId() {
		return locId;
	}

	public void setLocId(int locId) {
		this.locId = locId;
	}

	public String getSubLocs() {
		return subLocs;
	}

	public void setSubLocs(String subLocs) {
		this.subLocs = subLocs;
	}

	public String getParentLocs() {
		return parentLocs;
	}

	public void setParentLocs(String parentLocs) {
		this.parentLocs = parentLocs;
	}

	@Action("list")
	public String list(){
		locTree =  "[" + locService.getAllLocations() + "]";
		return "list";
	}
	
	@Action("subloc")
	public String subLoc(){
		
		subLocs = locService.getLocationsByFid(pid);
		subLocs = subLocs.replace(",\"selected\":false", "");
		
		return "subloc";
	}
	
	@Action("parloc")
	public String parLoc(){
		
		parentLocs = locService.getParentLocById(locId);
		return "parloc";
	}
}
