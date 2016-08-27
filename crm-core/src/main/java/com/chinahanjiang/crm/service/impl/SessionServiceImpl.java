package com.chinahanjiang.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.service.SessionService;
import com.chinahanjiang.crm.service.UserService;
import com.chinahanjiang.crm.util.MD5Util;
import com.chinahanjiang.crm.util.UserSession;

@Service("sessionService")
public class SessionServiceImpl implements SessionService {

	@Resource
	private UserService userService;

	@Override
	public UserDto logon(UserDto _UserDto) {

		UserDto userDto = userService.loadUserDto(_UserDto);
		
		if (userDto != null
				&& MD5Util.MD5(_UserDto.getPassword()).equals(userDto.getPassword())) {
			
			return userDto;
		}
		
		return null;
	}

	@Override
	public boolean logoff(UserDto ud) {

		if (UserSession.get(ud.getCardName()) != null) {
			
			return true;
		}
		return false;
	}

}
