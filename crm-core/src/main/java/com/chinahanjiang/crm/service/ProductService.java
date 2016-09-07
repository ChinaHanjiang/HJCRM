package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.pojo.Product;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public interface ProductService {

	SearchResultDto searchAndCount(int productCatalogId, String order,
			String sort, int page, int row);

	SearchResult<Product> searchAndCount(Search search);

	boolean save(Product p);

	MessageDto update(ProductDto pd);

	MessageDto delete(ProductDto pd);

	Product findById(int pId);

	String getProductsByCatalogId(int catalogId);

	ProductDto findById(ProductDto pd);

}
