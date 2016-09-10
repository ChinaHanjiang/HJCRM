package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ProductQuoteDao;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.ProductQuote;
import com.chinahanjiang.crm.pojo.User;
import com.chinahanjiang.crm.service.ItemService;
import com.chinahanjiang.crm.service.ProductQuoteService;
import com.chinahanjiang.crm.service.UserService;

@Service("productQuoteService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProductQuoteServiceImpl implements ProductQuoteService {

	@Resource
	private ProductQuoteDao productQuoteDao;
	
	@Resource
	private ItemService itemService;
	
	@Resource
	private UserService userService;

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
	
	
}
