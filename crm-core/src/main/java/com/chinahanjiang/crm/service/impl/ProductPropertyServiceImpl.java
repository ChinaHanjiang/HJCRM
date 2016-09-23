package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ProductPropertyDao;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductPropertyDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Product;
import com.chinahanjiang.crm.pojo.ProductProperty;
import com.chinahanjiang.crm.pojo.User;
import com.chinahanjiang.crm.service.ProductPropertyService;
import com.chinahanjiang.crm.service.ProductService;
import com.chinahanjiang.crm.service.UserService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;

@Service("productPropertyService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProductPropertyServiceImpl implements ProductPropertyService {

	@Resource
	private ProductPropertyDao productPropertyDao;
	
	@Resource
	private ProductService productService;
	
	@Resource
	private UserService userService;

	@Override
	public List<ProductPropertyDto> loadProductProperties(int productId) {
		
		Product p = productService.findById(productId);
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		search.addFilterEqual("product", p);
		List<ProductProperty> productProperties = productPropertyDao.search(search);
		List<ProductPropertyDto> ppds;
		ppds = DataUtil.convertPropertiesToDto(productProperties);
		
		return ppds;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(ProductProperty pp){
		
		return productPropertyDao.save(pp);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto update(ProductPropertyDto ppd, UserDto ud) {
		
		MessageDto md = new MessageDto();
		String message = "";
		Timestamp now = new Timestamp(System.currentTimeMillis());
		ProductProperty pp = null;
		int uid = ud.getId();
		User user = userService.findById(uid);
		
		int ppId = ppd.getId();
		if(ppId!=0){
			
			pp = productPropertyDao.find(ppId);
			if(pp!=null){
				
				pp.setUpdateTime(now);
				message = "产品属性修改成功！";
			}
			
		} else {
			
			pp = new ProductProperty();
			pp.setCreateTime(now);
			
			if(user!=null){
				
				pp.setUser(user);
			}
			message = "产品属性添加成功！";
		}
		
		if(pp!=null){
			
			pp.setName(ppd.getName());
			pp.setValue(ppd.getValue());
			pp.setEname(ppd.getEname());
			pp.setEvalue(ppd.getEvalue());
			pp.setRemarks(ppd.getRemarks());
			
			save(pp);
			
			md.setT(true);
			md.setMessage(message);
			return md;
			
			
		} else {
			
			md.setT(false);
			md.setMessage("产品属性信息找不到!");
			
		}
		
		return md;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto delete(ProductPropertyDto ppd) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		String ids = ppd.getIds();
		if(ids!=null){
			
			String[] arrs = ids.split(",");
			for(String i : arrs){
				
				int id = Integer.valueOf(i);
				
				ProductProperty pp = productPropertyDao.find(id);
				if(pp!=null){
					
					pp.setIsDelete(0);
					pp.setUpdateTime(now);
					
					save(pp);
				}
			}
			
			md.setT(true);
			md.setMessage("产品属性删除成功！");
			
		} else {
			
			md.setT(false);
			md.setMessage("要删除的Id不能为空！");
		}
		
		return md;
	}
	
}
