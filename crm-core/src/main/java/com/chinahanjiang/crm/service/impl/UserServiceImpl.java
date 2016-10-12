package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.UserDao;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.dto.UserDto;
import com.chinahanjiang.crm.pojo.User;
import com.chinahanjiang.crm.service.UserService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("userService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(User user){
		
		return userDao.save(user);
	}
	
	@Override
	public SearchResultDto searchAndCount(String order, String sort, int page,
			int row) {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		
		search.setMaxResults(row);
		search.setPage(page - 1 < 0 ? 0 : page - 1);
		SearchResult<User> result = searchAndCount(search);
		List<User> uls = result.getResult();

		List<UserDto> uds = DataUtil.convertUserToDto(uls);
		int records = result.getTotalCount();

		SearchResultDto srd = new SearchResultDto();
		srd.getRows().clear();
		srd.getRows().addAll(uds);
		srd.setTotal(records);
		
		return srd;
	}

	private SearchResult<User> searchAndCount(Search search) {
		
		return userDao.searchAndCount(search);
	}

	@Override
	public MessageDto update(UserDto ud) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		User u = null;
		int id = ud.getId();
		
		if(id == 0){
			
			u = new User();
			u.setCreateTime(now);
			
		} else {
			
			u = findById(id);
			if(u!=null){
				
				u.setUpdateTime(now);
			}
		}
		
		if(u!=null){
			
			u.setUserName(ud.getName());
			u.setCardName(ud.getCardName());
			u.setMobilephone(ud.getMobilephone());
			u.setEmail(ud.getEmail());
			u.setDuty(ud.getDuty());
			u.setSex(ud.getSexId());
			u.setRemarks(ud.getRemarks());
			
			boolean isR = save(u);
			if(isR){
				
				md.setT(true);
				md.setMessage("用户信息添加成功！");
			} else {
				
				md.setT(true);
				md.setMessage("用户信息修改成功！");
			}
			
		} else {
			
			md.setT(false);
			md.setMessage("用户信息添加失败！");
		}
		
		return md;
	}

	public User findById(int id) {
		
		return userDao.find(id);
	}

	@Override
	public MessageDto delete(UserDto ud) {

		MessageDto md = new MessageDto();
		User u = null;
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		int id = ud.getId();
		if(id!=0){
			
			u = findById(id);
			if(u!=null){
				
				u.setIsDelete(0);
				u.setUpdateTime(now);

				save(u);
				
				md.setT(true);
				md.setMessage("用户信息修改成功！");
				
			} else {
				
				md.setT(false);
				md.setMessage("用户在数据库不存在！");
			}
			
		} else {
			
			md.setT(false);
			md.setMessage("删除的用户的ID不能为空！");
		}
		
		return md;
	}

	@Override
	public UserDto loadUserDto(UserDto ud) {

		User user = null;
		if (ud.getCardName() != null) {
			
			user = findUserByName(ud.getCardName(), null);
		}

		if (user != null) {

			ud = DataUtil.convertUserTouDto(user);
			
		} else {

			return null;
		}
		
		return ud;
	}

	@Override
	public User findUserByName(String cardName, String md5) {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		search.addFilterEqual("cardName", cardName);
		User u = null;
		try{
			
			u = userDao.searchUnique(search);
		}catch(Exception e){
			
			System.out.println("'" + cardName + "'的在数据库中存在多个，请重新确定!" );
		}
		
		return u;
	}

	@Override
	public User findUserByCardName(String name) {
		
		User user = null;
		Search search = new Search();
		search.addFilterEqual("cardName", name);
		
		try{
			user = userDao.searchUnique(search);
		}catch(Exception e) {
			
			return null;
			
		}
		return user;
	}

	@Override
	public User findByUsersLogin(String username) {
		return null;
	}

	
}
