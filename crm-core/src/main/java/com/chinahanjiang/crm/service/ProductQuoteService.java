package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.UserDto;

public interface ProductQuoteService {

	MessageDto add(int itemId, UserDto ud);

}
