package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.TaskDao;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.TaskDto;
import com.chinahanjiang.crm.dto.TaskTypeDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.pojo.Product;
import com.chinahanjiang.crm.pojo.Task;
import com.chinahanjiang.crm.pojo.TaskType;
import com.chinahanjiang.crm.pojo.User;
import com.chinahanjiang.crm.service.ContactService;
import com.chinahanjiang.crm.service.CustomerService;
import com.chinahanjiang.crm.service.ItemService;
import com.chinahanjiang.crm.service.ProductService;
import com.chinahanjiang.crm.service.TaskService;
import com.chinahanjiang.crm.service.TaskTypeService;
import com.chinahanjiang.crm.service.UserService;
import com.chinahanjiang.crm.util.DataUtil;
import com.chinahanjiang.crm.util.DateUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("taskService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class TaskServiceImpl implements TaskService {

	@Resource
	private TaskDao taskDao;

	@Resource
	private ProductService productService;

	@Resource
	private UserService userService;

	@Resource
	private ContactService contactService;

	@Resource
	private CustomerService customerService;

	@Resource
	private TaskTypeService taskTypeService;

	@Resource
	private ItemService itemService;

	@Override
	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row) {

		return searchAndCount(order, sort, page, row, null, null, -1, 0, 0, null, null);
	}

	private SearchResult<Task> searchAndCount(Search search) {

		return taskDao.searchAndCount(search);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(Task task) {

		return taskDao.save(task);
	}

	@Override
	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row, String begin, String end, int status, int taskId,
			int tasktypeId, String name, String customerName) {

		Search search = new Search();
		search.addFilterEqual("isDelete", 1);

		if (begin != null && !begin.equals("")) {

			Timestamp beginTime = DateUtil.convertStrToBeginTime2(begin);
			search.addFilterGreaterOrEqual("createTime", beginTime);
		}

		if (end != null && !end.equals("")) {
			Timestamp endTime = DateUtil.convertStrToEndTime2(end);
			search.addFilterLessOrEqual("createTime", endTime);
		}

		if (status != -1) {

			search.addFilterEqual("status", status);
		}

		if (taskId != 0) {

			search.addFilterEqual("id", taskId);
		}
		
		if(tasktypeId != 0){
			
			TaskType tt = taskTypeService.findById(tasktypeId);
			if(tt!=null){
				search.addFilterEqual("taskType", tt);
			}
		}
		
		if(name!=null && !name.equals("")){
			
			search.addFilterLike("name", "%" + name + "%");
		}
		
		if(customerName != null && !customerName.equals("")){
			
			List<Customer> cs = customerService.findLikeName(customerName);
			if(cs!=null && cs.size()!=0){
				
				search.addFilterIn("customer", cs);
			}
		}

		search.setMaxResults(row);
		search.setPage(page - 1 < 0 ? 0 : page - 1);
		search.addSort("createTime", true);
		SearchResult<Task> result = searchAndCount(search);
		List<Task> cls = result.getResult();

		List<TaskDto> blds = DataUtil.convertTaskToDto(cls);
		int records = result.getTotalCount();

		SearchResultDto srd = new SearchResultDto();
		srd.getRows().clear();
		srd.getRows().addAll(blds);
		srd.setTotal(records);

		return srd;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto update(TaskDto td, UserDto u) {

		Timestamp now = new Timestamp(System.currentTimeMillis());
		MessageDto md = new MessageDto();
		String message = "";
		User user = null;
		Customer customer = null;
		TaskType taskType = null;
		Task t = null;

		int customerId = td.getCustomerId();
		customer = customerService.findById(customerId);

		int tasktypeId = td.getTaskTypeId();
		taskType = taskTypeService.findById(tasktypeId);
		
		if (u != null) {

			int uid = u.getId();
			user = userService.findById(uid);
		}

		int tid = td.getId();

		if (tid != 0) {

			/* 更新任务 */

			t = taskDao.find(tid);
			if (t != null) {

				t.setUpdateTime(now);
				t.setStatus(td.getStatus());

				message = "id为：" + tid + "的任务修改成功！";

			} else {

				md.setT(false);
				md.setMessage("任务在数据库中找不到！");
			}
		} else {

			/* 新增任务 */

			t = new Task();
			if (user != null) {

				t.setCreateUser(user);
				t.setUpdateUser(user);
				t.setCreateTime(now);
				t.setUpdateTime(now);
				t.setStatus(0);
				
				TaskTypeDto ttd = DataUtil.convertTaskTypeToDto(taskType);
				String code = generateCode(ttd);
				t.setCode(code);

				message = "新的项目创建成功!";

			} else {

				md.setT(false);
				md.setMessage("创建者不存在，请重新登陆！");
				return md;
			}

			
			if (taskType != null) {

				t.setTaskType(taskType);
			} else {

				md.setT(false);
				md.setMessage("任务类型不存在，请重新确认！");
				return md;
			}
		}

		if (customer != null) {

			t.setCustomer(customer);
		}

		t.setName(td.getName());
		t.setRemarks(td.getRemarks());

		String[] addProductIds = td.getAddProducts() == null ? null : td
				.getAddProducts().split(",");
		String[] deleteProductIds = td.getDeleteProducts() == null ? null : td
				.getDeleteProducts().split(",");

		if (addProductIds != null && addProductIds.length != 0) {

			t.setFlag(1);
		}

		if (deleteProductIds != null && deleteProductIds.length != 0) {

			t.setFlag(1);
		}

		List<Product> addProducts = productService.findByIds(addProductIds);
		List<Product> deleteProducts = productService
				.findByIds(deleteProductIds);

		List<Product> taskProducts = t.getProducts();
		if (taskProducts == null) {
			taskProducts = new ArrayList<Product>();
		}

		Iterator<Product> it = deleteProducts.iterator();
		while (it.hasNext()) {

			Product p = it.next();
			taskProducts.remove(p);
		}

		taskProducts.addAll(addProducts);
		t.setProducts(taskProducts);

		save(t);

		int ntId = t.getId();

		md.setT(true);
		md.setIntF(ntId);
		md.setMessage(message);
		return md;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto delete(TaskDto td) {

		MessageDto md = new MessageDto();
		Task t = null;
		Timestamp now = new Timestamp(System.currentTimeMillis());

		String ids = td.getIds();
		
		if(ids!=null && !ids.equals("")){
			
			String[] idarrs = ids.split(",");
			
			for(String i : idarrs){
				
				int id = Integer.valueOf(i);
				
				t = findById(id);
				if (t != null) {

					t.setIsDelete(0);
					t.setUpdateTime(now);

					itemService.deleteItemsByTask(t);

					save(t);

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
	public Task findById(int id) {

		return taskDao.find(id);
	}

	@Override
	public String generateCode(TaskTypeDto ttd) {

		String result = "";
		int id = ttd.getId();
		if (id != 0) {

			TaskType tt = taskTypeService.findById(id);
			if (tt != null) {

				String code = tt.getCode();
				String day = DateUtil.getCurrentDayString();

				Search search = new Search();
				search.addFilterEqual("taskType", tt);

				Timestamp begin = DateUtil.getCurrentDayStartTime();
				Timestamp end = DateUtil.getCurrentDayEndTime();

				search.addFilterGreaterOrEqual("createTime", begin);
				search.addFilterLessOrEqual("createTime", end);

				int num = taskDao.count(search) + 1;

				result += code + "-" + day + "-" + DataUtil.converNumToStr(num);
			}
		}

		return result;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto updateProducts(TaskDto td) {

		MessageDto md = new MessageDto();
		int id = td.getId();
		if (id != 0) {

			Task t = findById(id);

			if (t != null) {

				td.setName(t.getName());
				td.setCode(t.getCode());
				td.setRemarks(t.getRemarks());

				md = update(td, null);
			}
		} else {

			md.setT(false);
			md.setMessage("项目：" + id + "在数据库中不存在！");

		}

		return md;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto finishTask(TaskDto td) {

		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		int id = td.getId();
		Task task = findById(id);

		task.setStatus(td.getStatus());
		task.setUpdateTime(now);

		//
		itemService.finishItemsByTask(task);

		taskDao.save(task);

		md.setT(true);
		md.setMessage("");

		return md;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto giveupTask(TaskDto td) {

		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		int id = td.getId();
		Task task = findById(id);

		task.setStatus(td.getStatus());
		task.setRemarks(task.getRemarks() + "--" + td.getRemarks());
		task.setUpdateTime(now);
		
		itemService.giveupItemsByTask(task);

		taskDao.save(task);

		md.setT(true);
		md.setMessage("");

		return md;
	}

}