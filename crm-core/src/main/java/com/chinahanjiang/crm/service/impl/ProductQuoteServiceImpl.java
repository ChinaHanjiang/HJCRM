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
import com.chinahanjiang.crm.dto.ProductQuoteDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
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
import com.chinahanjiang.crm.service.TaskService;
import com.chinahanjiang.crm.service.UserService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;

@Service("productQuoteService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProductQuoteServiceImpl implements ProductQuoteService {

	@Resource
	private ProductQuoteDao productQuoteDao;
	
	@Resource
	private ItemService itemService;
	
	@Resource
	private TaskService taskService;
	
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
				List<Product> products = productService.findByItem(i);
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
								price += pc.getSproduct().getStandardPrice()*pc.getQuantity();
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
	public boolean save(ProductQuote pq) {
		
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

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteQuoteByItem(Item i) {
		
		ProductQuote pq = findProductQuoteByItem(i);
		if(pq!=null){
			pq.setIsDelete(0);
			save(pq);
		}
	}

	@Override
	public MessageDto findProductQuoteByItemId(int itemId) {
		
		MessageDto md = new MessageDto();
		
		
		Item item = itemService.findById(itemId);
		if(item!=null){
			
			int flag = item.getFlag();
			if(flag==1){
				
				md.setT(false);
				md.setMessage("报价单已经报了价!");
				
			} else {
				
				ProductQuote pq = findProductQuoteByItem(item);
				if(pq!=null){
					
					md.setT(true);
					md.setIntF(pq.getId());
				} else {
					
					md.setT(false);
					md.setMessage("报价单找不到！");
				}
			}
		}
		
		return md;
	}

	@Override
	public SearchResultDto searchAndCount(int taskId, String order,
			String sort, int page, int row) {
		
		SearchResultDto srd = new SearchResultDto();
		Task task = taskService.findById(taskId);
		if(task!=null){
			
			List<Item> items = itemService.findItemsByTaskForQuote(task);
			if(items!=null){
				
				List<ProductQuote> pqs = findProductQuoteByItems(items);
				List<ProductQuoteDto> pqsd = DataUtil.convertProductQuoteToDto(pqs);
				
				srd.setTotal(pqs.size());
				srd.getRows().clear();
				srd.getRows().addAll(pqsd);
			}
		}
		
		return srd;
	}

	private List<ProductQuote> findProductQuoteByItems(List<Item> items) {
		
		Search search = new Search();
		search.addFilterIn("item", items);
		search.addSort("createTime", true);
		return productQuoteDao.search(search);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto closeProductQuote(ProductQuoteDto pqd) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		int id = pqd.getId();
		ProductQuote pq = findById(id);
		
		if(pq!=null){
			
			pq.setStatus(1);
			pq.setUpdateTime(now);
			
			Item item = pq.getItem();
			item.setFlag(1);
			item.setUpdateTime(now);
			
			Task task = item.getTask();
			task.setFlag(0);
			task.setUpdateTime(now);
			
			save(pq);
			
			md.setT(true);
		} else {
			
			md.setT(false);
			md.setMessage("id为：" + id + "的报价单找不到");
		}
		
		return md;
	}

	private ProductQuote findById(int id) {
		
		return productQuoteDao.find(id);
	}
}

