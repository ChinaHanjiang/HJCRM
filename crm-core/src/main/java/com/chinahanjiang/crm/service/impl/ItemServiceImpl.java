package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ItemDao;
import com.chinahanjiang.crm.dto.ItemDto;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.TaskDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Contact;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.ItemType;
import com.chinahanjiang.crm.pojo.Task;
import com.chinahanjiang.crm.pojo.User;
import com.chinahanjiang.crm.service.ContactService;
import com.chinahanjiang.crm.service.CustomerService;
import com.chinahanjiang.crm.service.ItemService;
import com.chinahanjiang.crm.service.ItemTypeService;
import com.chinahanjiang.crm.service.TaskService;
import com.chinahanjiang.crm.service.UserService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("itemService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ItemServiceImpl implements ItemService {

	@Resource
	private ItemDao itemDao;

	@Resource
	private TaskService taskService;

	@Resource
	private UserService userService;

	@Resource
	private CustomerService customerService;

	@Resource
	private ItemTypeService itemTypeService;

	@Resource
	private ContactService contactService;

	@Override
	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row, Timestamp begin, Timestamp end, int i, TaskDto td) {

		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		search.addFilterEqual("status", i);
		
		if(td!=null){
			
			Task task = taskService.findById(td.getId());
			if(task!=null){
				
				search.addFilterEqual("task", task);
			}
		}
		
		if (begin != null) {
			search.addFilterGreaterOrEqual("createTime", begin);
		}

		if (end != null) {
			search.addFilterLessOrEqual("createTime", end);
		}

		if (i != 0) {
			search.addFilterEqual("status", i);
		}

		search.setMaxResults(row);
		search.setPage(page - 1 < 0 ? 0 : page - 1);
		SearchResult<Item> result = searchAndCount(search);
		List<Item> ils = result.getResult();

		List<ItemDto> ids = DataUtil.convertItemToDto(ils);
		int records = result.getTotalCount();

		SearchResultDto srd = new SearchResultDto();
		srd.getRows().clear();
		srd.getRows().addAll(ids);
		srd.setTotal(records);

		return srd;
	}

	private SearchResult<Item> searchAndCount(Search search) {

		return itemDao.searchAndCount(search);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto update(ItemDto id, TaskDto td, UserDto ud) {

		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Item i = null;
		User user = null;
		Customer customer = null;
		Contact contact = null;
		Task task = null;
		String message = "";
		String addProducts = td.getAddProducts();
		String deleteProducts = td.getDeleteProducts();

		int tId = td.getId();
		if (tId != 0) {

			task = taskService.findById(tId);
		}

		if (task == null) {

			md.setMessage("项目不能为空！");
			md.setT(false);

			return md;
		}

		if ((addProducts != null && !addProducts.equals(""))
				|| (deleteProducts != null && !deleteProducts.equals(""))) {

			md = taskService.updateProducts(td);
		}

		if (!md.isT()) {

			return md;

		} else {

			int cId = id.getCustomerId();
			customer = customerService.findById(cId);

			int ctId = id.getContactId();
			contact = contactService.findById(ctId);

			if (ud != null) {

				int uid = ud.getId();
				user = userService.findById(uid);
			}

			int iId = id.getId();
			if (iId != 0) {

				i = findById(iId);
				if (i != null) {

					i.setUpdateTime(now);
					i.setStatus(0);

					message = "任务修改成功！";

				} else {

					md.setT(false);
					md.setMessage("任务在数据库中找不到！");
				}

			} else {

				i = new Item();
				i.setCreateTime(now);
				i.setStatus(0);
				i.setTask(task);

				message = "任务添加成功!";

				if (user != null) {

					i.setUser(user);
					i.setCreateTime(now);
					i.setStatus(0);

					message = "新的任务创建成功!";

				} else {

					md.setT(false);
					md.setMessage("创建者不存在，请重新登陆！");
					return md;
				}
			}

			if (customer != null) {

				i.setCustomer(customer);
			}

			if (contact != null) {

				i.setContact(contact);
			}

			int itemTypeId = id.getItemTypeId();
			ItemType itemType = itemTypeService.findById(itemTypeId);
			if (itemType != null) {

				i.setItemType(itemType);
			} else {

				md.setT(false);
				md.setMessage("任务类型不存在，请重新确认！");
				return md;
			}

			i.setName(id.getName());
			i.setCode(id.getCode());
			i.setRemarks(id.getRemarks());

			save(i);

			int niId = i.getId();

			md.setT(true);
			md.setIntF(niId);
			md.setMessage(message);
			return md;
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(Item i) {

		return itemDao.save(i);
	}

	@Override
	public Item findById(int iId) {

		return itemDao.find(iId);
	}

}
