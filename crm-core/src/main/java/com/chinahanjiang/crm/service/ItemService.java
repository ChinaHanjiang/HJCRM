package com.chinahanjiang.crm.service;

import java.sql.Timestamp;

import com.chinahanjiang.crm.dto.SearchResultDto;

public interface ItemService {

	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row, Timestamp begin, Timestamp end, int i);

}
