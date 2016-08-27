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

import com.chinahanjiang.crm.dto.LocationDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.service.LocationService;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/loc")
@Results({ @Result(name = "error", location = "/error.jsp"),
	@Result(name="list",type="json"),
	@Result(name="subloc",type="json"),
	@Result(name="parloc",type="json"),
	@Result(name="add",type="json"),
	@Result(name="modify",type="json"),
	@Result(name="delete",type="json"),
	@Result(name="check",type="json")})
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
	
	private MessageDto md;
	
	private LocationDto ld;
	
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
	
	public MessageDto getMd() {
		return md;
	}

	public void setMd(MessageDto md) {
		this.md = md;
	}

	public LocationDto getLd() {
		return ld;
	}

	public void setLd(LocationDto ld) {
		this.ld = ld;
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
	
	@Action("add")
	public String add(){
		
		md = locService.update(ld);
		
		return "add";
	}
	
	@Action("modify")
	public String modify(){
		
		md = locService.update(ld);
		return "modify";
	}
	
	@Action("del")
	public String delete(){
		
		md = locService.delete(ld);
		return "delete";
	}
	
	@Action("check")
	public String check(){
		
		md = locService.check(ld);
		return "check";
	}
}
