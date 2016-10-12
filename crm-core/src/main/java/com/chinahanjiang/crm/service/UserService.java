package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.exception.LoginException;
import com.chinahanjiang.crm.exception.SystemRunException;
import com.chinahanjiang.crm.pojo.User;

public interface UserService {

	SearchResultDto searchAndCount(String order, String sort, int page, int row);

	public boolean save(User user);

	MessageDto update(UserDto ud);

	MessageDto delete(UserDto ud);

	User findById(int id);

	UserDto loadUserDto(UserDto _UserDto);

	User findUserByName(String trim, String md5);

	User findUserByCardName(String name);

	User findByUsersLogin(String username) throws LoginException,
			SystemRunException;
}
