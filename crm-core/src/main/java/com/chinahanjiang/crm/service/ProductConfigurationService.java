package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductConfigurationDto;
import com.chinahanjiang.crm.pojo.Product;
import com.chinahanjiang.crm.pojo.ProductConfiguration;

public interface ProductConfigurationService {

	public boolean save(ProductConfiguration pcf);
	
	public boolean remove(ProductConfiguration pcf);

	public ProductConfiguration findByFProductAndSProduct(Product fProduct,
			Product sProduct);

	public List<ProductConfigurationDto> loadProductMixs(
			ProductConfigurationDto pcfd);

	public MessageDto saveProductMix(int productId, String inserted,
			String updated, String deleted);

	public List<ProductConfiguration> findByFProduct(Product p);

	boolean removeById(int id);
	
}
