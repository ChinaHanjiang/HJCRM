package com.chinahanjiang.crm.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.chinahanjiang.crm.pojo.Groups;
import com.chinahanjiang.crm.pojo.Location;
import com.chinahanjiang.crm.service.GroupsService;
import com.chinahanjiang.crm.service.LocationService;

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
	
	@Resource
	private LocationService locService;
	
	@Resource
	private GroupsService groupsService;
	
	private List<Location> locations;
	
	private List<Groups> groups;
	
	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
	
	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}

	@Action("customerlist")
	public String customerListWin(){
		
		/*初始化一级区域信息*/
		locations = locService.loadLocationsByPid(1);
		
		/*加载集团信息*/
		groups = groupsService.loadGroups();
		
		return "customerlist";
	}

}
