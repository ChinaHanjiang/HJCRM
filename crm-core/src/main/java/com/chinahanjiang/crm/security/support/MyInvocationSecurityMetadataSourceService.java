package com.chinahanjiang.crm.security.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.chinahanjiang.crm.service.AuthoritiesResourcesService;
import com.chinahanjiang.crm.service.AuthoritiesService;

public class MyInvocationSecurityMetadataSourceService implements
	FilterInvocationSecurityMetadataSource {

	@Autowired
	private AuthoritiesResourcesService authotitiesResourcesService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public MyInvocationSecurityMetadataSourceService() {
		loadResourceDefine();
	}

	private void loadResourceDefine() {
		
		List<String> auths = authoritiesService.findAllAuthoritiesName();
		/**
		 * 应当是资源为key，权限为value。资源通常为url，权限就是那些以ROLE_为前缀的角色。一个资源可以由多个权限访问
		 */
		resourceMap = new HashMap<String,Collection<ConfigAttribute>>();
		for(String auth : auths){
			
		ConfigAttribute ca = new SecurityConfig(auth);
		
		List<String> resources = authotitiesResourcesService.findResourcesByAuthName(auth);
		
			for(String res : resources){
				String url = res;
				
				/**
				 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限
				 * 集合，将权限增加到权限集合中
				 */
				
				if(resourceMap.containsKey(url)){
					
					Collection<ConfigAttribute> value = resourceMap.get(url);
					value.add(ca);
					resourceMap.put(url, value);
				} else {
					
					Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
					atts.add(ca);
					resourceMap.put(url, atts);
				}
			}
		}
	}
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {  
		  
		  return null;  
	}
	
	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object obj)
			throws IllegalArgumentException {
		
		FilterInvocation filterInvocation = (FilterInvocation)obj;
		
		//object是一个url，被用户请求的url
		String url = filterInvocation.getRequestUrl();
		int firstQuestionMarkIndex = url.indexOf("?");
		
		if (firstQuestionMarkIndex != -1) {  
            url = url.substring(0, firstQuestionMarkIndex);  
        }
		
		Iterator<String> ite = resourceMap.keySet().iterator();
		
		while(ite.hasNext()){
			
			String resUrl = ite.next();
			 RequestMatcher requestMatcher = new AntPathRequestMatcher(resUrl);
			    if(requestMatcher.matches(filterInvocation.getHttpRequest())) {
			        return resourceMap.get(resUrl);
			    }
		}
		
		return null;
	}
}
