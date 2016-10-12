package com.chinahanjiang.crm.service;

import java.util.List;

import com.chinahanjiang.crm.pojo.User;
import com.chinahanjiang.crm.pojo.UserRoles;

public interface UserRolesService {

	List<UserRoles> findRolesByUser(User user);

}
