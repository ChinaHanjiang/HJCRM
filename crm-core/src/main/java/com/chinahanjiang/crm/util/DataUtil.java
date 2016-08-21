package com.chinahanjiang.crm.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.chinahanjiang.crm.dto.ComboResultDto;
import com.chinahanjiang.crm.dto.ContactDto;
import com.chinahanjiang.crm.dto.CustomerDto;
import com.chinahanjiang.crm.dto.EyTreeDto;
import com.chinahanjiang.crm.pojo.Contact;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.pojo.Location;
import com.google.gson.Gson;

public class DataUtil {

	private static DateFormat sdf_d = new SimpleDateFormat("yyyy/MM/dd");

	private static SimpleDateFormat sdf_dt = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static List<CustomerDto> convertCustomerToDto(List<Customer> cls) {
		// TODO Auto-generated method stub

		List<CustomerDto> cds = new ArrayList<CustomerDto>();
		Iterator<Customer> it = cls.iterator();
		while (it.hasNext()) {

			Customer c = it.next();
			CustomerDto cd = new CustomerDto();

			cd.setId(c.getId());
			cd.setName(c.getName());
			cd.setCode(c.getCode());
			cd.setAddress(c.getAddress());
			cd.setGroupsId(c.getGroups()==null ? 0 : c.getGroups().getId());
			cd.setGroups(c.getGroups()==null ? "" : c.getGroups().getName());
			cd.setLocId(c.getLocation() == null ? 0 : c.getLocation().getId());
			cd.setLocation(c.getLocation() == null ? "" : c.getLocation()
					.getName());
			cd.setTelephone(c.getTelephone());
			cd.setFax(c.getFax());
			cd.setRemarks(c.getRemarks());

			cd.setCreateTime(c.getCreateTime() == null ? "" : sdf_dt.format(c
					.getCreateTime()));
			cd.setUpdateTime(c.getUpdateTime() == null ? "" : sdf_dt.format(c
					.getUpdateTime()));

			cd.setUser(c.getUser()==null ? "" : c.getUser().getName());
			cds.add(cd);
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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
				cd.setUserId(c.getUser()==null ? 0 : c.getUser().getId());
				cd.setUser(c.getUser()==null ? "" : c.getUser().getName());
				cd.setCustomerId(c.getCustomer()==null ? 0 : c.getCustomer().getId());
				cd.setCustomerName(c.getCustomer()==null ? "" : c.getCustomer().getName());
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
		// TODO Auto-generated method stub
		
		List<ComboResultDto> crds = new ArrayList<ComboResultDto>();
		ComboResultDto crd = null;
		int i = 1;
		
		if(location!=null){
			
			List<Location> childLocs = location.getChildLocs();
			Iterator<Location> it = childLocs.iterator();
			while(it.hasNext()){
				
				Location loc = it.next();
				int isDelete = loc.getIsDelete();
				if(isDelete!=0){
					
					crd = new ComboResultDto();
					crd.setId(loc.getId());
					crd.setText(loc.getName());
					if(i==1){
						crd.setSelected(true);
						i++;
					}
					crds.add(crd);
				}
			}
		}
		return crds;
	}
	
	public static List<Location> getAllLocChildren(Location loc){
		
		List<Location> locs = new ArrayList<Location>();
		
		List<Location> childLocs = loc.getChildLocs();
		if(childLocs!=null && childLocs.size()!=0){
			
			Iterator<Location> it = childLocs.iterator();
			while(it.hasNext()){
				
				Location next = it.next();
				List<Location> nextLocs = getAllLocChildren(next);
				locs.addAll(nextLocs);
			}
		} else {
			
			locs.add(loc);
		}
		
		return locs;
	}
}
