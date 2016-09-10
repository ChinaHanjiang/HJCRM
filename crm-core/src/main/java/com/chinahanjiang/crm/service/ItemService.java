package com.chinahanjiang.crm.service;

import java.sql.Timestamp;

import com.chinahanjiang.crm.dto.ItemDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.TaskDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Item;

public interface ItemService {

	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row, Timestamp begin, Timestamp end, int i);

	MessageDto update(ItemDto id, TaskDto td, UserDto ud);

	boolean save(Item i);

	public Item findById(int itemId);

}
