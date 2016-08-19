package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.SearchResultDto;

public interface ContactService {

	SearchResultDto searchAndCount(int customerId, String order, String sort,
			int page, int row);

}
