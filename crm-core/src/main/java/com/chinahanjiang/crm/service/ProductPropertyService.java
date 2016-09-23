package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductPropertyDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.ProductProperty;

public interface ProductPropertyService {

	List<ProductPropertyDto> loadProductProperties(int productId);

	MessageDto update(ProductPropertyDto ppd, UserDto ud);

	MessageDto delete(ProductPropertyDto ppd);

	boolean save(ProductProperty pp);

}
