package com.chinahanjiang.crm.service;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductCatalogDto;
import com.chinahanjiang.crm.pojo.ProductCatalog;

public interface ProductCatalogService {

	ProductCatalog findProductCatalogByCode(String pCode);

	boolean save(ProductCatalog pc);

	ProductCatalog findProductCatalogByCode(String pCode, ProductCatalog ppPc);

	String getAllCatalogs();

	MessageDto update(ProductCatalogDto pcd);

	MessageDto delete(ProductCatalogDto pcd);

	MessageDto check(ProductCatalogDto pcd);

	ProductCatalog findById(int parentId);

	void delete(ProductCatalog pc);

	String initCatalogTree(ProductCatalogDto pcd);

	String getCatalogsByParentId(int id);

	ProductCatalog findProductCatalogByCodeAndName(String pcCode, String catalog);
	
}
