package com.chinahanjiang.crm.service;

import java.util.List;

public interface AuthoritiesResourcesService {

	List<String> findResourcesByAuthName(String auth);

}
