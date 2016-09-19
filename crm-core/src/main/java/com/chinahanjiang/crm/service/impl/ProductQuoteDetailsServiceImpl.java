package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ProductQuoteDetailsDao;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductConfigurationDto;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.Product;
import com.chinahanjiang.crm.pojo.ProductAndQuoteRelation;
import com.chinahanjiang.crm.pojo.ProductQuote;
import com.chinahanjiang.crm.pojo.ProductQuoteDetails;
import com.chinahanjiang.crm.service.ItemService;
import com.chinahanjiang.crm.service.ProductAndQuoteRelationService;
import com.chinahanjiang.crm.service.ProductQuoteDetailsService;
import com.chinahanjiang.crm.service.ProductQuoteService;
import com.chinahanjiang.crm.service.ProductService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;

@Service("productQuoteDetailsService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProductQuoteDetailsServiceImpl implements
		ProductQuoteDetailsService {

	@Resource
	private ProductService productService;
	
	@Resource
	private ItemService itemService;
	
	@Resource
	private ProductQuoteService productQuoteService;
	
	@Resource
	private ProductQuoteDetailsDao productQuoteDetailsDao;
	
	@Resource
	private ProductAndQuoteRelationService productAndQuoteRelationService;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto saveProductMixForQuote(int itemId, int productId,
			String inserted, String updated, String deleted) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Double totalPrice = 0.0;
		Double price = 0.0;
		Product p = productService.findById(productId);
		Item item = itemService.findById(itemId);
		
		ProductQuote pq =null;
		if(item!=null){
			pq= productQuoteService.findProductQuoteByItem(item);
			totalPrice = pq.getPrice();
		}
		
		ProductQuoteDetails pqd = null;
		
		if(pq!=null && p!=null){
			
			pqd = findPqdByProductAndQuote(p,pq);
			price = pqd.getPrice();
		}
		
		if(pqd!=null){
			
			//delete对象
			if(deleted!=null){
				
				List<ProductConfigurationDto> deletePds = DataUtil.convertJsonToProductConfigurationDto(deleted);
				Iterator<ProductConfigurationDto> it = deletePds.iterator();
				while(it.hasNext()){
					
					ProductConfigurationDto pcfd = it.next();
					int rid = pcfd.getId();
					
					if(rid!=0){
						
						ProductAndQuoteRelation pqr = productAndQuoteRelationService.findById(rid);
						
						price -= pqr.getDefindPrice()*pqr.getQuantity();
						totalPrice -= price;
						
						boolean isR = productAndQuoteRelationService.removeById(rid);
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
					
					ProductAndQuoteRelation pqr = productAndQuoteRelationService.findById(uid);
					
					if(pqr!=null){
						
						price -= pqr.getDefindPrice()*pqr.getQuantity();
						price += pd.getDefinedPrice()*pd.getQuantity();
						totalPrice += price;
						
						Product sp = pqr.getProduct();
						int ospid = sp.getId();
						
						if(ospid==nspid){
							//修改
							
							pqr.setQuantity(pd.getQuantity());
							pqr.setDefindPrice(pd.getDefinedPrice());
							pqr.setRemarks(pd.getRemarks());
							pqr.setUpdateTime(now);
							productAndQuoteRelationService.save(pqr);
							
							
						} else {
							
							//删除后增加
							
							productAndQuoteRelationService.removeById(uid);
							
							//新增
							Product nsp = productService.findById(nspid);
							ProductAndQuoteRelation npqr = new ProductAndQuoteRelation();
							npqr.setProduct(nsp);
							npqr.setProductQuoteDetails(pqd);
							npqr.setQuantity(pd.getQuantity());
							npqr.setDefindPrice(pd.getDefinedPrice());
							npqr.setCreateTime(now);
							npqr.setRemarks(pd.getRemarks());
							
							productAndQuoteRelationService.save(npqr);
						}
					}
				}
			}
			
			//insert对象
			if(inserted!=null){
				
				List<ProductConfigurationDto> insertPds = DataUtil.convertJsonToProductConfigurationDto(inserted);
				
				Iterator<ProductConfigurationDto> it = insertPds.iterator();
				while(it.hasNext()){
					
					ProductConfigurationDto pd = it.next();
					
					int nspId = pd.getSpid();
					Product nsp = productService.findById(nspId);
					
					ProductAndQuoteRelation npqr = new ProductAndQuoteRelation();
					npqr.setProduct(nsp);
					npqr.setProductQuoteDetails(pqd);
					npqr.setQuantity(pd.getQuantity());
					npqr.setDefindPrice(pd.getDefinedPrice());
					npqr.setCreateTime(now);
					npqr.setRemarks(pd.getRemarks());
					
					productAndQuoteRelationService.save(npqr);
					
					price += pd.getDefinedPrice()*pd.getQuantity();
					totalPrice += price;
				}
				
			}
			
		}
		
		//更新pqd和pq的价格
		if(item!=null){
			
			pq= productQuoteService.findProductQuoteByItem(item);
			pq.setPrice(totalPrice);
			productQuoteService.save(pq);
		}
		
		if(p!=null && pq!=null){
			
			pqd = findPqdByProductAndQuote(p,pq);
			pqd.setPrice(price);
			productQuoteDetailsDao.save(pqd);
		}
		
		return md;
	}

	private ProductQuoteDetails findPqdByProductAndQuote(Product p, ProductQuote pq) {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		search.addFilterEqual("product", p);
		search.addFilterEqual("productQuote", pq);
		
		return productQuoteDetailsDao.searchUnique(search);
	}

	@Override
	public List<ProductConfigurationDto> loadProductMixs(
			int productQuoteDetailId) {
		
		List<ProductConfigurationDto> pcds = new ArrayList<ProductConfigurationDto>();
		ProductQuoteDetails pqd = findById(productQuoteDetailId);
		if(pqd!=null){
			
			List<ProductAndQuoteRelation> pqrs = productAndQuoteRelationService.findByProductQuoteDetails(pqd);
			
			pcds.addAll(DataUtil.convertPqrsToPcds(pqrs));
		}
		return pcds;
	}

	private ProductQuoteDetails findById(int productQuoteDetailId) {
		
		return productQuoteDetailsDao.find(productQuoteDetailId);
	}
	
	
}
