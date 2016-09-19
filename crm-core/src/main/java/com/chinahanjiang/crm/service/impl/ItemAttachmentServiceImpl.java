package com.chinahanjiang.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ItemAttachmentDao;
import com.chinahanjiang.crm.service.ItemAttachmentService;

@Service("itemAttachmentService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ItemAttachmentServiceImpl implements ItemAttachmentService {

	@Resource
	private ItemAttachmentDao itemAttachmentDao;
}
