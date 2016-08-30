package com.chinahanjiang.crm.security.v1;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfo implements UserDetails,CredentialsContainer {

	private static final long serialVersionUID = 1L;

	private String password;
	
	private String name;//用户的中文姓名
	
	private String email;//用户的邮箱地址
	
	private final String username;
	
	private final Set<GrantedAuthority> authorities;
	
	private final boolean accountNonExpired;
	
	private final boolean accountNonLocked;
	
	private final boolean credentialsNonExpired;
	
	private final boolean enabled;
	
	public UserInfo(String password, String name, String email, final String username,
			final Collection<? extends GrantedAuthority> authorities,
			final boolean accountNonExpired, final boolean accountNonLocked,
			final boolean credentialsNonExpired, final boolean enabled){
		if(username == null || "".equals(username) || password == null)
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		
		this.password = password;
		this.name = name;
		this.email = email;
		this.username = username;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private Set<? extends GrantedAuthority> sortAuthorities(
			Collection<? extends GrantedAuthority> authorities) {
		
		return null;
	}

	@Override
	public void eraseCredentials() {
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
