package com.chinahanjiang.crm.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.service.SessionService;
import com.chinahanjiang.crm.util.Constant;

@Controller
@ParentPackage("struts-default")
@Namespace("/login")
@Results({ @Result(name = "error", location = "/error.jsp"),
		@Result(name = "success", location = "/WEB-INF/content/main.jsp"),
		@Result(name = "logOut", location = "/login.jsp"),
		@Result(name = "toLogin", location = "/login.jsp")})
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private SessionService sessionService;
	
	private UserDto ud;

	public UserDto getUd() {
		return ud;
	}

	public void setUd(UserDto ud) {
		this.ud = ud;
	}

	@Action("on")
	public String login() {
		
		//验证用户
		UserDto _user = sessionService.logon(ud);
		
		if(_user!=null){
			
			this.session.put(Constant.USERKEY, _user);
		
			return SUCCESS;
		} else {
			
			//setMessage("用户不存在或密码错误！");
			return "toLogin";
		}
	}

	@Action("out")
	public String logout() {
		
		//验证用户
		session.remove(Constant.USERKEY);
		
		return "logOut";
	}

}
