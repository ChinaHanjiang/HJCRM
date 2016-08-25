package com.chinahanjiang.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.dto.UserSessionDto;
import com.chinahanjiang.crm.service.SessionService;
import com.chinahanjiang.crm.service.UserService;

@Service("sessionService")
public class SessionServiceImpl implements SessionService {

	@Resource
	private UserService userService;

	@Override
	public UserDto logon(UserDto _UserDto) {

		UserDto userDto = userService.getUserDto(_UserDto);
		if (userDto != null
				&& _UserDto.getPassword().equals(userDto.getPassword())) {
			return userDto;
		}
		return null;

	}

	@Override
	public boolean logoff(UserDto _UserDto) {

		if (UserSessionDto.get("user") != null) {
			return true;
		}
		return false;
	}

}
