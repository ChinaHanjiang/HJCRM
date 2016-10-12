package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.ItemDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.UserDto;

public interface ItemAttachmentService {

	MessageDto save(ItemDto id, UserDto ud);

}
