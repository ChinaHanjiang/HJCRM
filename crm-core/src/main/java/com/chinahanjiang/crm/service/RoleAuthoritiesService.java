package com.chinahanjiang.crm.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface RoleAuthoritiesService {

	Collection<GrantedAuthority> loadUserAuthoritiesByName(String username);

}
