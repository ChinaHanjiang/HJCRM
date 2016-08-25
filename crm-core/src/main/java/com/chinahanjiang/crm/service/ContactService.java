package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.dto.ContactDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;

public interface ContactService {

	SearchResultDto searchAndCount(int customerId, String order, String sort,
			int page, int row);

	MessageDto update(ContactDto cd);

	MessageDto delete(ContactDto cd);

	List<ContactDto> search(ContactDto cd);
}
