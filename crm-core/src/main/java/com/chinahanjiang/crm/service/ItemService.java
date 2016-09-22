 package com.chinahanjiang.crm.service;

import java.sql.Timestamp;
import java.util.List;

import com.chinahanjiang.crm.dto.ItemDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.TaskDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.Task;

public interface ItemService {

	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row, Timestamp begin, Timestamp end, int i, TaskDto td);

	MessageDto update(ItemDto id, TaskDto td, UserDto ud);

	boolean save(Item i);

	public Item findById(int itemId);

	public void deleteItemsByTask(Task t);

	public MessageDto delete(ItemDto id);

	public MessageDto checkStatus(TaskDto td);

	public void finishItemsByTask(Task task);

	public void giveupItemsByTask(Task task);

	public MessageDto finishItem(ItemDto id);

	public List<Item> findItemsByTask(Task task);

	public MessageDto addQuoteItem(TaskDto td, UserDto ud);

	List<Item> findItemsByTaskForQuote(Task task);

}
