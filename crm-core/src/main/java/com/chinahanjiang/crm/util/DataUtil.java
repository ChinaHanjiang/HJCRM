package com.chinahanjiang.crm.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.chinahanjiang.crm.dto.ComboResultDto;
import com.chinahanjiang.crm.dto.ContactDto;
import com.chinahanjiang.crm.dto.CustomerDto;
import com.chinahanjiang.crm.dto.DataListDto;
import com.chinahanjiang.crm.dto.EyTreeDto;
import com.chinahanjiang.crm.dto.GroupsDto;
import com.chinahanjiang.crm.dto.ItemDto;
import com.chinahanjiang.crm.dto.TaskDto;
import com.chinahanjiang.crm.dto.TaskTypeDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.Contact;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.pojo.Groups;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.Location;
import com.chinahanjiang.crm.pojo.Task;
import com.chinahanjiang.crm.pojo.TaskType;
import com.chinahanjiang.crm.pojo.User;
import com.google.gson.Gson;

public class DataUtil {

	// private static DateFormat sdf_d = new SimpleDateFormat("yyyy/MM/dd");

	private static SimpleDateFormat sdf_dt = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static List<CustomerDto> convertCustomerToDto(List<Customer> cls) {

		List<CustomerDto> cds = new ArrayList<CustomerDto>();

		if (cls != null) {
			Iterator<Customer> it = cls.iterator();
			while (it.hasNext()) {

				Customer c = it.next();
				CustomerDto cd = new CustomerDto();

				cd.setId(c.getId());
				cd.setName(c.getName());
				cd.setCode(c.getCode());
				cd.setAddress(c.getAddress());
				cd.setGroupsId(c.getGroups() == null ? 0 : c.getGroups()
						.getId());
				cd.setGroups(c.getGroups() == null ? "" : c.getGroups()
						.getName());
				cd.setLocId(c.getLocation() == null ? 0 : c.getLocation()
						.getId());
				cd.setLocation(c.getLocation() == null ? "" : c.getLocation()
						.getName());
				cd.setTelephone(c.getTelephone());
				cd.setFax(c.getFax());
				cd.setRemarks(c.getRemarks());

				cd.setCreateTime(c.getCreateTime() == null ? "" : sdf_dt
						.format(c.getCreateTime()));
				cd.setUpdateTime(c.getUpdateTime() == null ? "" : sdf_dt
						.format(c.getUpdateTime()));

				cd.setUser(c.getUser() == null ? "" : c.getUser().getName());
				cds.add(cd);
			}
		}
		return cds;
	}

	public static String locationToJson(Location loc) {

		Gson gson = new Gson();
		EyTreeDto btd = convertLocationToDto(loc);
		String str = null;
		if (btd != null) {
			str = gson.toJson(btd);
		}
		return str;
	}

	private static EyTreeDto convertLocationToDto(Location loc) {

		EyTreeDto btd = null;
		int isDelete = loc.getIsDelete();
		if (loc != null && isDelete != 0) {
			btd = new EyTreeDto();
			btd.setId(loc.getId());
			btd.setText(loc.getName() + "-" + loc.getCode());
			btd.setState(loc.getState());

			List<Location> children = loc.getChildLocs();
			if (children != null && children.size() != 0) {
				btd.setIsF(1);
				List<EyTreeDto> btdchild = new ArrayList<EyTreeDto>();
				EyTreeDto btdc = null;
				for (Location t : children) {

					btdc = convertLocationToDto(t);
					if (btdc != null) {
						btdchild.add(btdc);
					}
				}

				btd.setChildren(btdchild);
			} else {

				btd.setIsF(0);
			}
		}

		return btd;

	}

	public static List<ContactDto> convertContactToDto(List<Contact> cls) {

		List<ContactDto> ctds = new ArrayList<ContactDto>();
		if (cls != null) {
			Iterator<Contact> it = cls.iterator();
			while (it.hasNext()) {

				Contact c = it.next();
				ContactDto cd = new ContactDto();
				cd.setId(c.getId());
				cd.setName(c.getName());
				cd.setPhone(c.getMobilePhone());
				cd.setEmail(c.getEmail());
				cd.setDuty(c.getDuty());
				cd.setSexId(c.getSex());
				cd.setSex(c.getSex() == 1 ? "男" : "女");
				cd.setRemarks(c.getRemarks());
				cd.setCreateTime(c.getCreateTime() == null ? "" : sdf_dt
						.format(c.getCreateTime()));
				cd.setUpdateTime(c.getUpdateTime() == null ? "" : sdf_dt
						.format(c.getUpdateTime()));
				cd.setUserId(c.getUser() == null ? 0 : c.getUser().getId());
				cd.setUser(c.getUser() == null ? "" : c.getUser().getName());
				cd.setCustomerId(c.getCustomer() == null ? 0 : c.getCustomer()
						.getId());
				cd.setCustomerName(c.getCustomer() == null ? "" : c
						.getCustomer().getName());
				ctds.add(cd);
			}
		}

		return ctds;
	}

	public static String locationCrdToJson(Location location) {

		Gson gson = new Gson();
		List<ComboResultDto> crds = convertLocationToCRD(location);
		String str = null;
		if (crds != null) {
			str = gson.toJson(crds);
		}
		return str;
	}

	private static List<ComboResultDto> convertLocationToCRD(Location location) {

		List<ComboResultDto> crds = new ArrayList<ComboResultDto>();
		ComboResultDto crd = null;
		int i = 1;

		if (location != null) {

			List<Location> childLocs = location.getChildLocs();
			Iterator<Location> it = childLocs.iterator();
			while (it.hasNext()) {

				Location loc = it.next();
				int isDelete = loc.getIsDelete();
				if (isDelete != 0) {

					crd = new ComboResultDto();
					crd.setId(loc.getId());
					crd.setText(loc.getName());
					if (i == 1) {
						crd.setSelected(true);
						i++;
					}
					crds.add(crd);
				}
			}
		}
		return crds;
	}

	public static List<Location> getAllLocChildren(Location loc) {

		List<Location> locs = new ArrayList<Location>();

		List<Location> childLocs = loc.getChildLocs();
		if (childLocs != null && childLocs.size() != 0) {

			Iterator<Location> it = childLocs.iterator();
			while (it.hasNext()) {

				Location next = it.next();
				List<Location> nextLocs = getAllLocChildren(next);
				locs.addAll(nextLocs);
			}
		} else {

			locs.add(loc);
		}

		return locs;
	}

	public static List<GroupsDto> convertGroupsToDto(List<Groups> cls) {

		List<GroupsDto> gds = new ArrayList<GroupsDto>();

		if (cls != null) {

			Iterator<Groups> it = cls.iterator();
			while (it.hasNext()) {

				Groups g = it.next();

				GroupsDto gd = new GroupsDto();
				gd.setId(g.getId());
				gd.setName(g.getName());
				gd.setCode(g.getCode());
				gd.setRemarks(g.getRemarks());
				gd.setUser(g.getUser() == null ? "" : g.getUser().getName());
				gd.setUserId(g.getUser() == null ? 0 : g.getUser().getId());

				gd.setCreateTime(g.getCreateTime() == null ? "" : sdf_dt
						.format(g.getCreateTime()));
				gd.setUpdateTime(g.getUpdateTime() == null ? "" : sdf_dt
						.format(g.getUpdateTime()));

				gds.add(gd);
			}
		}

		return gds;
	}

	public static List<TaskTypeDto> convertTaskTypeToDto(List<TaskType> tts) {

		List<TaskTypeDto> ttds = new ArrayList<TaskTypeDto>();

		if (tts != null) {

			Iterator<TaskType> it = tts.iterator();
			while (it.hasNext()) {

				TaskType tt = it.next();
				TaskTypeDto ttd = new TaskTypeDto();
				ttd.setId(tt.getId());
				ttd.setName(tt.getName());
				ttd.setCode(tt.getCode());
				ttd.setRemarks(tt.getRemarks());
				ttd.setCreateTime(tt.getCreateTime() == null ? "" : sdf_dt
						.format(tt.getCreateTime()));
				ttd.setUpdateTime(tt.getUpdateTime() == null ? "" : sdf_dt
						.format(tt.getUpdateTime()));
				ttd.setUser(tt.getUser() == null ? "" : tt.getUser().getName());
				ttd.setUserId(tt.getUser() == null ? 0 : tt.getUser().getId());

				ttds.add(ttd);
			}
		}
		return ttds;
	}

	public static List<TaskDto> convertTaskToDto(List<Task> ts) {

		List<TaskDto> tds = new ArrayList<TaskDto>();
		if (ts != null) {

			Iterator<Task> it = ts.iterator();
			while (it.hasNext()) {

				Task t = it.next();
				TaskDto td = new TaskDto();

				td.setId(t.getId());
				td.setName(t.getName());
				td.setCode(t.getCode());
				td.setTaskType(t.getTaskType() == null ? "" : t.getTaskType()
						.getName());
				td.setTaskTypeId(t.getTaskType() == null ? 0 : t.getTaskType()
						.getId());
				td.setCustomer(t.getCustomer() == null ? "" : t.getCustomer()
						.getName());
				td.setCustomerId(t.getCustomer() == null ? 0 : t.getCustomer()
						.getId());
				td.setCreateUser(t.getCreateUser() == null ? "" : t
						.getCreateUser().getName());
				td.setCreateUserId(t.getCreateUser() == null ? 0 : t
						.getCreateUser().getId());
				td.setCreateTime(t.getCreateTime() == null ? "" : sdf_dt
						.format(t.getCreateTime()));
				td.setUpdateUser(t.getUpdateUser() == null ? "" : t
						.getUpdateUser().getName());
				td.setUpdateUserId(t.getUpdateUser() == null ? 0 : t
						.getUpdateUser().getId());
				td.setUpdateTime(t.getUpdateTime() == null ? "" : sdf_dt
						.format(t.getUpdateTime()));
				td.setRemarks(t.getRemarks());
				td.setStatus(t.getStatus());
				td.setItemNum(t.getItems() == null ? 0 : t.getItems().size());

				tds.add(td);
			}
		}

		return tds;
	}

	public static String convertCustomersCrdToJson(List<Customer> cls) {

		Gson gson = new Gson();
		List<ComboResultDto> crds = convertCustomersToCrds(cls);
		String str = null;
		if (crds != null) {
			str = gson.toJson(crds);
		}
		return str;
	}

	private static List<ComboResultDto> convertCustomersToCrds(
			List<Customer> cls) {

		List<ComboResultDto> crds = new ArrayList<ComboResultDto>();
		ComboResultDto crd = null;
		int i = 1;
		if (cls != null) {

			Iterator<Customer> it = cls.iterator();
			while (it.hasNext()) {

				Customer c = it.next();
				crd = new ComboResultDto();
				crd.setId(c.getId());
				crd.setText(c.getName());
				if (i == 1) {
					crd.setSelected(true);
				}

				crds.add(crd);
			}
		}
		return crds;
	}

	public static List<DataListDto> convertCustomerToDld(List<Customer> cls) {

		List<DataListDto> dlds = new ArrayList<DataListDto>();
		if (cls != null) {

			Iterator<Customer> it = cls.iterator();
			while (it.hasNext()) {

				Customer c = it.next();
				DataListDto dld = new DataListDto();
				dld.setId(c.getId());
				dld.setText(c.getName());

				dlds.add(dld);
			}
		}
		return dlds;
	}

	public static List<UserDto> convertUserToDto(List<User> uls) {
		// TODO Auto-generated method stub

		List<UserDto> uds = new ArrayList<UserDto>();

		if (uls != null) {

			Iterator<User> it = uls.iterator();
			while (it.hasNext()) {

				User user = it.next();
				UserDto u = new UserDto();

				u.setId(user.getId());
				u.setName(user.getName());
				u.setCardName(user.getCardName());
				u.setMobilephone(user.getMobilephone());
				u.setEmail(user.getEmail());
				u.setDuty(user.getDuty());
				u.setRemarks(user.getRemarks());
				u.setSexId(user.getSex());
				u.setSex(user.getSex() == 1 ? "男" : "女");
				u.setCreateTime(user.getCreateTime() == null ? "" : sdf_dt
						.format(user.getCreateTime()));
				u.setUpdateTime(user.getUpdateTime() == null ? "" : sdf_dt
						.format(user.getUpdateTime()));

				uds.add(u);
			}
		}
		return uds;
	}

	public static UserDto convertUserTouDto(User user) {

		UserDto ud = new UserDto();
		if (user != null) {

			ud.setId(user.getId());
			ud.setName(user.getName());
			ud.setCardName(user.getName());
			ud.setPassword(user.getPassword());
			ud.setDuty(user.getDuty());
			ud.setEmail(user.getEmail());
			ud.setMobilephone(user.getMobilephone());
			ud.setRemarks(user.getRemarks());
			ud.setSexId(user.getSex());
			ud.setSex(user.getSex() == 1 ? "男" : "女");
		}

		return ud;
	}

	public static List<ItemDto> convertItemToDto(List<Item> ils) {

		List<ItemDto> ids = new ArrayList<ItemDto>();

		if (ils != null) {

			Iterator<Item> it = ils.iterator();
			while (it.hasNext()) {

				Item i = it.next();
				ItemDto id = new ItemDto();
				id.setId(i.getId());
				id.setName(i.getName());
				id.setCode(i.getCode());
				id.setCustomer(i.getCustomer() == null ? "" : i.getCustomer()
						.getName());
				id.setCustomerId(i.getCustomer() == null ? 0 : i.getCustomer()
						.getId());
				id.setRemarks(i.getRemarks());
				id.setStatus(i.getStatus());
				id.setTask(i.getTask() == null ? "" : i.getTask().getName());
				id.setTaskId(i.getTask() == null ? 0 : i.getTask().getId());
				id.setTasktype(i.getTask() == null ? "" : (i.getTask()
						.getTaskType() == null ? "" : i.getTask().getTaskType()
						.getName()));
				id.setTasktypeId(i.getTask() == null ? 0 : (i.getTask()
						.getTaskType() == null ? 0 : i.getTask().getTaskType()
						.getId()));
				id.setUser(i.getUser() == null ? "" : i.getUser().getName());
				id.setUserId(i.getUser() == null ? 0 : i.getUser().getId());
				id.setCreateTime(i.getCreateTime() == null ? "" : sdf_dt
						.format(i.getCreateTime()));
				id.setUpdateTime(i.getUpdateTime() == null ? "" : sdf_dt
						.format(i.getUpdateTime()));
				id.setContact(i.getContact() == null ? "" : i.getContact().getName());
				id.setContactId(i.getCode() == null ? 0 : i.getContact().getId());
				
				ids.add(id);
			}
		}

		return ids;
	}

	public static List<DataListDto> convertGroupsToDld(List<Groups> gls) {
		
		List<DataListDto> dlds = new ArrayList<DataListDto>();
		if (gls != null) {

			Iterator<Groups> it = gls.iterator();
			while (it.hasNext()) {

				Groups g = it.next();
				DataListDto dld = new DataListDto();
				dld.setId(g.getId());
				dld.setText(g.getName());
				dld.setCode(g.getCode());

				dlds.add(dld);
			}
		}
		
		return dlds;
	}

}
