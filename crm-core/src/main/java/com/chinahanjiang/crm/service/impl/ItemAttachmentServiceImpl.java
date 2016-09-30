package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ItemAttachmentDao;
import com.chinahanjiang.crm.dto.ItemDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.ItemAttachment;
import com.chinahanjiang.crm.pojo.User;
import com.chinahanjiang.crm.service.ItemAttachmentService;
import com.chinahanjiang.crm.service.ItemService;
import com.chinahanjiang.crm.service.UserService;

@Service("itemAttachmentService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ItemAttachmentServiceImpl implements ItemAttachmentService {

	@Resource
	private ItemAttachmentDao itemAttachmentDao;
	
	@Resource
	private ItemService itemService;
	
	@Resource
	private UserService userService;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto save(ItemDto id, UserDto ud) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		int uid = ud.getId();
		User user = userService.findById(uid);
		
		int iid = id.getId();
		Item item = itemService.findById(iid);
		
		if(item!=null && item.getIsDelete()==1){
			
			String attachments = id.getAttachments();
			if(attachments!=null){
				
				String[] paths = attachments.split(",");
				for(String path : paths){
					
					ItemAttachment ia = new ItemAttachment();
					ia.setCreateTime(now);
					ia.setItem(item);
					ia.setLocation(path.trim());
					if(user!=null){
						
						ia.setUser(user);
					}

					String[] name = path.trim().split("\\\\");
					int length = name.length;
					ia.setName(name[length-1]);
					
					itemAttachmentDao.save(ia);
					
				}
			}
			
			item.setUpdateTime(now);
			itemService.save(item);
			
			md.setT(true);
			
		} else {
			
			md.setT(false);
			md.setMessage("任务找不到！");
		}
		
		return md;
	}
}
