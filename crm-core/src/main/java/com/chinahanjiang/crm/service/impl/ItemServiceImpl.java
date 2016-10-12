package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.chinahanjiang.crm.dto.TaskTypeDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Contact;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.ItemType;
import com.chinahanjiang.crm.pojo.Product;
import com.chinahanjiang.crm.pojo.Task;
import com.chinahanjiang.crm.pojo.TaskType;
import com.chinahanjiang.crm.pojo.User;
import com.chinahanjiang.crm.service.ContactService;
import com.chinahanjiang.crm.service.CustomerService;
import com.chinahanjiang.crm.service.ItemService;
import com.chinahanjiang.crm.service.ItemTypeService;
import com.chinahanjiang.crm.service.ProductQuoteService;
import com.chinahanjiang.crm.service.ProductService;
import com.chinahanjiang.crm.service.TaskService;
import com.chinahanjiang.crm.service.UserService;
import com.chinahanjiang.crm.util.Constant;
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

	@Resource
	private ProductService productService;

	@Resource
	private ProductQuoteService productQuoteService;
	
	@Override
	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row, Timestamp begin, Timestamp end, int i, TaskDto td) {

		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		
		if (td != null) {

			Task task = taskService.findById(td.getId());
			if (task != null) {

				search.addFilterEqual("task", task);
			}
		}

		if (begin != null) {
			search.addFilterGreaterOrEqual("createTime", begin);
		}

		if (end != null) {
			search.addFilterLessOrEqual("createTime", end);
		}

		if (i != -1) {
			search.addFilterEqual("status", i);
		}

		search.addSort("createTime", true);
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

		int tid = td.getId();
		task = taskService.findById(tid);
		
		int cId = id.getCustomerId();
		customer = customerService.findById(cId);

		int ctId = id.getContactId();
		contact = contactService.findById(ctId);
		
		int itemTypeId = id.getItemTypeId();
		ItemType itemType = itemTypeService.findById(itemTypeId);

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
			if(task!=null){
				
				i.setTask(task);
				TaskType tt = task.getTaskType();
				TaskTypeDto ttd = DataUtil.convertTaskTypeToDto(tt);
				
				String code = taskService.generateCode(ttd);
				Search search = new Search();
				search.addFilterEqual("task", task);
				int num = itemDao.count(search);
				i.setCode(code + "." + (num+1));
				
			} else {
				
				md.setT(false);
				md.setMessage(" 项目不存在，请重新确认！");
				return md;
			}
			
			
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

		if (itemType != null) {

			i.setItemType(itemType);
			
		} else {

			md.setT(false);
			md.setMessage("任务类型不存在，请重新确认！");
			return md;
		}

		int tId = td.getId();
		if (tId != 0) {

			task = taskService.findById(tId);
		}

		if (task == null) {

			md.setMessage("项目不能为空！");
			md.setT(false);

			return md;
		}

		String taskType = task.getTaskType().getName();
		if ((addProducts != null && !addProducts.equals(""))
				|| (deleteProducts != null && !deleteProducts.equals(""))) {
			if (!taskType.equals(Constant.GT)) {

				md = taskService.updateProducts(td);

				if (!md.isT()) {

					return md;

				} else {

					List<Product> products = productService.findByTask(task);
					if(i.getProducts()==null){
						List<Product> ps = new ArrayList<Product>();
						i.setProducts(ps);
					}
					i.getProducts().clear();
					i.getProducts().addAll(products);
				}

			} else {

				String[] addProductIds = addProducts == null ? null
						: addProducts.split(",");
				String[] deleteProductIds = deleteProducts == null ? null
						: deleteProducts.split(",");

				List<Product> addProduct = productService
						.findByIds(addProductIds);
				List<Product> deleteProduct = productService
						.findByIds(deleteProductIds);

				List<Product> itemProducts = i.getProducts();
				if (itemProducts == null) {
					itemProducts = new ArrayList<Product>();
				}

				Iterator<Product> it = deleteProduct.iterator();
				while (it.hasNext()) {

					Product p = it.next();
					itemProducts.remove(p);
				}

				itemProducts.addAll(addProduct);
				i.setProducts(itemProducts);
			}
		}
		i.setName(id.getName());
		i.setFlag(id.getFlag());
		i.setRemarks(id.getRemarks());

		save(i);

		int niId = i.getId();

		md.setT(true);
		md.setIntF(niId);
		md.setMessage(message);
		return md;

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

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteItemsByTask(Task t) {

		Search search = new Search();
		search.addFilterEqual("task", t);

		List<Item> items = itemDao.search(search);

		Iterator<Item> it = items.iterator();
		while (it.hasNext()) {

			Item i = it.next();

			i.setIsDelete(0);

			save(i);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto delete(ItemDto id) {
		
		MessageDto md = new MessageDto();
		Item i = null;
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		String ids = id.getIds();
		if(ids!=null && !ids.equals("")){
			
			String[] idarrs = ids.split(",");
			
			for(String is : idarrs){
				
				int idstr = Integer.valueOf(is);
				
				i = findById(idstr);
				if(i!=null){
					
					i.setIsDelete(0);
					i.setUpdateTime(now);
					
					productQuoteService.deleteQuoteByItem(i);
					save(i);
					
					md.setT(true);
					md.setMessage("任务信息修改成功！");
					
				} else {
					
					md.setT(false);
					md.setMessage("任务在数据库不存在！");
				}
			}
		} else {
			
			md.setT(false);
			md.setMessage("删除的任务的ID不能为空！");
		}
		
		return md;
	}

	@Override
	public MessageDto checkStatus(TaskDto td) {
		
		MessageDto md = new MessageDto();
		
		int tid = td.getId();
		
		if(tid!=0){
			
			Task task = taskService.findById(tid);
			
			if(task!=null){
				
				Search search = new Search();
				search.addFilterEqual("isDelete", 1);
				search.addFilterEqual("task", task);
				
				List<Item> is = itemDao.search(search);
				Iterator<Item> it = is.iterator();
				int j = 0;
				while(it.hasNext()){
					
					Item i = it.next();
					
					int status = i.getStatus();
					if(status == 0){
						
						j ++;
					}
				}
				
				if(j!=0){
					
					md.setT(true);
					md.setIntF(j);
					md.setMessage("总共有" + j + "个子任务没有完成！请确认");
					
					return md;
				} else {
					
					md.setT(true);
					md.setIntF(j);
					md.setMessage("ok");
					
					return md;
				}
				
			} else {
				
				md.setT(false);
				md.setMessage("任务id="+ tid + "在数据库中找不到！");
				
				return md;
				
			}
		} else {
			
			md.setT(false);
			md.setMessage("任务id不能为0！");
			
			return md;
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void finishItemsByTask(Task task) {
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Search search = new Search();
		search.addFilterEqual("task", task);
		List<Item> items = itemDao.search(search);
		
		Iterator<Item> it = items.iterator();
		while(it.hasNext()){
			
			Item i = it.next();
			
			i.setStatus(1);
			i.setUpdateTime(now);
			
			save(i);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void giveupItemsByTask(Task task) {
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Search search = new Search();
		search.addFilterEqual("task", task);
		List<Item> items = itemDao.search(search);
		
		Iterator<Item> it = items.iterator();
		while(it.hasNext()){
			
			Item i = it.next();
			
			i.setIsDelete(0);
			i.setUpdateTime(now);
			
			save(i);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto finishItem(ItemDto id) {
		
		MessageDto md = new MessageDto();
		String message = "";
		int j = 0;
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		String ids = id.getIds();
		if(ids!=null && !ids.equals("")){
			
			String[] arrs = ids.split(",");
			
			for(String is : arrs){
				
				int itemId = Integer.valueOf(is);
				
				Item item = findById(itemId);
				if(item!=null){
					
					/*ItemType itemType = item.getItemType();
					if("报价".indexOf(itemType.getName())!=-1){
						
						if(item.getFlag()==0){
							
							continue;
						}
					}*/
					//针对报价单未保存的情况
					if(item.getFlag()==0){
						
						message += item.getCode() + "报价单未保存,不能关闭！";
						j++;
						continue;
					}
					
					item.setStatus(1);
					item.setUpdateTime(now);
					
					save(item);
				}
			}
		}
		
		md.setT(true);
		md.setIntF(j);
		md.setMessage(message);
		
		return md;
	}

	@Override
	public List<Item> findItemsByTask(Task task) {
		
		Search search = new Search();
		search.addFilterEqual("task", task);
		
		return itemDao.search(search);
		
	}
	
	@Override
	public List<Item> findItemsByTaskForQuote(Task task) {
		
		Search search = new Search();
		search.addFilterEqual("task", task);
		search.addFilterEqual("flag", 1);
		
		return itemDao.search(search);
		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto addQuoteItem(TaskDto td, UserDto ud) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		int uid = ud.getId();
		User user = userService.findById(uid);
		
		int tid = td.getId();
		Task task = taskService.findById(tid);
		
		Item i = new Item();
		i.setName("项目-" + task.getCode() + "-报价");
		i.setFlag(0);
		task.setFlag(0);
		i.setTask(task);
		
		TaskType tt = task.getTaskType();
		TaskTypeDto ttd = DataUtil.convertTaskTypeToDto(tt);
		
		String code = taskService.generateCode(ttd);
		Search search = new Search();
		search.addFilterEqual("task", task);
		int num = itemDao.count(search);
		
		i.setCode(code + "." + (num+1));
		i.setStatus(0);
		i.setCustomer(task.getCustomer());
		i.setCreateTime(now);

		ItemType it = itemTypeService.findByName("产品报价");
		if(it!=null){
			i.setItemType(it);
		} else {
			
			md.setT(false);
			md.setMessage("产品类型找不到！");
			return md;
		}
		
		List<Product> products = productService.findByTask(task);
		i.setProducts(products);
		
		if(user!=null)
			i.setUser(user);
		i.setRemarks("任务自动生成");
		
		save(i);
		
		int itemId = i.getId();
		md.setT(true);
		md.setIntF(itemId);
		
		return md;
	}

}
