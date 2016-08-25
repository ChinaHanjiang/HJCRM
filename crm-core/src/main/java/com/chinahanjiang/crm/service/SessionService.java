package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.UserDto;

public interface SessionService {
 
	public UserDto logon(UserDto _UserDto);
	
	public boolean logoff(UserDto _UserDto);
	
}
