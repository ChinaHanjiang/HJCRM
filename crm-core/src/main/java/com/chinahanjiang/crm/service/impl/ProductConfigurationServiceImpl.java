package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ProductConfigurationDao;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductConfigurationDto;
import com.chinahanjiang.crm.pojo.Product;
import com.chinahanjiang.crm.pojo.ProductConfiguration;
import com.chinahanjiang.crm.service.ProductConfigurationService;
import com.chinahanjiang.crm.service.ProductService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;

@Service("productConfigurationService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProductConfigurationServiceImpl implements
		ProductConfigurationService {

	@Resource
	private ProductConfigurationDao productConfigurationDao;
	
	@Resource
	private ProductService productService;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(ProductConfiguration pcf) {
		
		return productConfigurationDao.save(pcf);
	}

	@Override
	public ProductConfiguration findByFProductAndSProduct(Product fProduct,
			Product sProduct) {
		
		Search search = new Search();
		search.addFilterEqual("fProduct", fProduct);
		search.addFilterEqual("sProduct", sProduct);
		ProductConfiguration pcf;
		try{
			
			pcf = productConfigurationDao.searchUnique(search);
		}catch(Exception e){
			
			 pcf = null;
		}
		
		return pcf;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean remove(ProductConfiguration pcf) {
		
		return productConfigurationDao.remove(pcf);
	}

	@Override
	public List<ProductConfigurationDto> loadProductMixs(
			ProductConfigurationDto pcfd) {
		
		List<ProductConfigurationDto> pdfds = 
				new ArrayList<ProductConfigurationDto>();
		int fpid = pcfd.getFpid();
		if(fpid!=0){
			
			Product p = productService.findById(fpid);
			
			if(p!=null){
				
				List<ProductConfiguration> pcfs = findByFProduct(p);
				
				pdfds.addAll(DataUtil.convertProductConfigurationsToDto(pcfs));
			}
			
		}
		
		return pdfds;
	}

	@Override
	public List<ProductConfiguration> findByFProduct(Product p) {
		
		Search search = new Search();
		search.addFilterEqual("fproduct", p);
		return productConfigurationDao.search(search);
	}

	/**
	 * 需不需要判断重复项？
	 */
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto saveProductMix(int productId, String inserted,
			String updated, String deleted) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		if(productId!=0){
			
			Product fp = productService.findById(productId);
			
			if(fp!=null){
				
				//delete对象
				if(deleted!=null){
					
					List<ProductConfigurationDto> deletePds = DataUtil.convertJsonToProductConfigurationDto(deleted);
					Iterator<ProductConfigurationDto> it = deletePds.iterator();
					while(it.hasNext()){
						
						ProductConfigurationDto pcfd = it.next();
						int rid = pcfd.getId();
						
						if(rid!=0){
							
							boolean isR = removeById(rid);
							if(!isR){
								
								//id为(id)的Product在组合中找不到
							}
						}
					}
				}
				
				//update对象
				if(updated!=null){
					
					List<ProductConfigurationDto> updatePds = DataUtil.convertJsonToProductConfigurationDto(updated);
					//delete掉Id，然后新增
					Iterator<ProductConfigurationDto> it = updatePds.iterator();
					while(it.hasNext()){
						
						ProductConfigurationDto pd = it.next();
						
						int uid = pd.getId();
						int nspid = pd.getSpid();
						
						ProductConfiguration pcf = findById(uid);
						
						if(pcf!=null){
							
							Product sp = pcf.getSproduct();
							int ospid = sp.getId();
							
							if(ospid==nspid){
								//修改
								pcf.setQuantity(pd.getQuantity());
								pcf.setRemarks(pd.getRemarks());
								pcf.setUpdateTime(now);
								
								save(pcf);
								
							} else {
								
								//删除后增加
								
								removeById(uid);
								
								//新增
								Product nsp = productService.findById(nspid);
								ProductConfiguration npcf = new ProductConfiguration();
								npcf.setFproduct(fp);
								npcf.setSproduct(nsp);
								npcf.setQuantity(pd.getQuantity());
								npcf.setCreateTime(now);
								
								save(npcf);
							}
						}
					}
				}
				
				//insert对象
				if(inserted!=null){
					
					List<ProductConfigurationDto> insertPds = DataUtil.convertJsonToProductConfigurationDto(inserted);
					
					Iterator<ProductConfigurationDto> it = insertPds.iterator();
					while(it.hasNext()){
						
						ProductConfigurationDto pcfd = it.next();
						
						int nspId = pcfd.getSpid();
						Product nsp = productService.findById(nspId);
						
						ProductConfiguration npcf = new ProductConfiguration();
						npcf.setFproduct(fp);
						npcf.setSproduct(nsp);
						npcf.setQuantity(pcfd.getQuantity());
						npcf.setRemarks(pcfd.getRemarks());
						npcf.setCreateTime(now);
						
						save(npcf);
					}
					
				}
				
				
			} else {
				
				md.setT(false);
				md.setMessage("产品Id：" + productId + "在数据库中查找不在！");
			}
		}
		
		return md;
	}

	private ProductConfiguration findById(int id) {
		
		return productConfigurationDao.find(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean removeById(int id) {
		
		return productConfigurationDao.removeById(id);
	}
	
	
}
