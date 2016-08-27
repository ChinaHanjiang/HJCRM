package com.chinahanjiang.crm.interceptor;

import java.util.Map;

import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.service.SessionService;
import com.chinahanjiang.crm.service.UserService;
import com.chinahanjiang.crm.util.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	
	private SessionService sessionService;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		

		ActionContext actionContext = invocation.getInvocationContext();
		Map<String,Object> session = actionContext.getSession();
		
		if(session!=null && session.get(Constant.USERKEY)!=null){
			UserDto user = (UserDto) session.get(Constant.USERKEY);
			//查找用户是否存在
			
			if(user != null ){
				
				return invocation.invoke();
			} else {
				session.remove(Constant.USERKEY);
				return "toLogin";
			}
		}else {
			return "toLogin";
		}

	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
}
