package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ProductQuoteDao;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.Product;
import com.chinahanjiang.crm.pojo.ProductAndQuoteRelation;
import com.chinahanjiang.crm.pojo.ProductConfiguration;
import com.chinahanjiang.crm.pojo.ProductQuote;
import com.chinahanjiang.crm.pojo.ProductQuoteDetails;
import com.chinahanjiang.crm.pojo.Task;
import com.chinahanjiang.crm.pojo.User;
import com.chinahanjiang.crm.service.ItemService;
import com.chinahanjiang.crm.service.ProductConfigurationService;
import com.chinahanjiang.crm.service.ProductQuoteService;
import com.chinahanjiang.crm.service.ProductService;
import com.chinahanjiang.crm.service.UserService;
import com.googlecode.genericdao.search.Search;

@Service("productQuoteService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProductQuoteServiceImpl implements ProductQuoteService {

	@Resource
	private ProductQuoteDao productQuoteDao;
	
	@Resource
	private ItemService itemService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private ProductService productService;
	
	@Resource
	private ProductConfigurationService productConfigurationService;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto add(int itemId,UserDto ud) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		User user = null;
		
		int uid = ud.getId();
		if(uid!=0){
			
			user = userService.findById(uid);
		}
		if(itemId!=0){
			
			Item i = itemService.findById(itemId);
			if(i!=null){
				
				ProductQuote pq = new ProductQuote();
				pq.setCode("Q-"+i.getCode());
				pq.setItem(i);
				pq.setCreateTime(now);
				if(user!=null)
					pq.setUser(user);
				
				//初始化ProductQuoteDetails
				List<ProductQuoteDetails> pqds = new ArrayList<ProductQuoteDetails>();
				Task task = i.getTask();
				List<Product> products = productService.findByTask(task);
				double totalPrice = 0.0;
				if(products!=null){
					
					Iterator<Product> it = products.iterator();
					while(it.hasNext()){
						
						Product p = it.next();
						ProductQuoteDetails pqd = new ProductQuoteDetails();
						List<ProductAndQuoteRelation> pqrs = new ArrayList<ProductAndQuoteRelation>();
						List<ProductConfiguration> pcs = productConfigurationService.findByFProduct(p);
						Double price = 0.0;
						
						if(pcs!=null){
							
							Iterator<ProductConfiguration> pcIt = pcs.iterator();
							
							while(pcIt.hasNext()){
								
								ProductConfiguration pc = pcIt.next();
								ProductAndQuoteRelation pqr = new ProductAndQuoteRelation();
								pqr.setDefindPrice(pc.getSproduct().getStandardPrice());
								pqr.setProduct(pc.getSproduct());
								pqr.setProductQuoteDetails(pqd);
								pqr.setQuantity(pc.getQuantity());
								pqr.setRemarks(pc.getRemarks());
								price += pc.getSproduct().getStandardPrice();
								pqr.setCreateTime(now);
								pqrs.add(pqr);
							}
						}
						
						pqd.setProducts(pqrs);
						
						pqd.setProductQuote(pq);
						pqd.setProduct(p);
						totalPrice += price;
						pqd.setPrice(price);
						
						pqds.add(pqd);
					}
				}
				
				pq.setProductQuoteDetails(pqds);
				pq.setPrice(totalPrice);
				
				save(pq);
				int pqId = pq.getId();
				md.setIntF(pqId);
				md.setT(true);
				md.setMessage("任务号为:"+itemId+"成功生成报价单！");
				
			} else {
				
				md.setT(false);
				md.setMessage("任务号为:"+itemId+"查找不到！");
			}
		} else {
			
			md.setT(false);
			md.setMessage("任务号不能为0！");
		}
	
		
		return md;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private boolean save(ProductQuote pq) {
		
		return productQuoteDao.save(pq);
	}

	@Override
	public List<ProductQuoteDetails> findProductQuoteDetailsByItem(Item item) {
		
		List<ProductQuoteDetails> pqds = new ArrayList<ProductQuoteDetails>();
		if(item!=null){
			
			Search search = new Search();
			search.addFilterEqual("isDelete", 1);
			search.addFilterEqual("item", item);
			
			ProductQuote pq = productQuoteDao.searchUnique(search);
			if(pq!=null){
				
				pqds.addAll(pq.getProductQuoteDetails());
			}
		}
		
		return pqds;
	}

	@Override
	public ProductQuote findProductQuoteByItem(Item item) {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		search.addFilterEqual("item", item);
		return productQuoteDao.searchUnique(search);
	}
}

