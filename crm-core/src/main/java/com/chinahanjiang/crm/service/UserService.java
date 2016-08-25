package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.User;

public interface UserService {

	SearchResultDto searchAndCount(String order, String sort, int page, int row);

	public boolean save(User user);
	
	MessageDto update(UserDto ud);

	MessageDto delete(UserDto ud);
	
	User findById(int id);
}
