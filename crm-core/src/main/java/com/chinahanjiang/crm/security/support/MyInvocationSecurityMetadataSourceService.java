package com.chinahanjiang.crm.security.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.chinahanjiang.crm.security.dao.PubAuthoritiesResourcesDao;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果，此类在初始化时，应渠道所有资源及其对应角色的定义。
 * @author tree
 *
 */

public class MyInvocationSecurityMetadataSourceService implements
	FilterInvocationSecurityMetadataSource {

	@Autowired
	private PubAuthoritiesResourcesDao pubAuthoritiesResourcesDao;

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	private Session session;

	public MyInvocationSecurityMetadataSourceService() {
		loadResourceDefine();
	}

	private void loadResourceDefine() {
		
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");
		
		SessionFactory sessionFactory = (SessionFactory)context.getBean("sessionFactory");
		
		session = sessionFactory.openSession();
		
		String username = "";
		String sql = "";
		
		//在web服务器启动时，提取系统中的所有权限
		sql = "select authority_name from pub_authorities";
		
		@SuppressWarnings("unchecked")
		List<String> query = session.createSQLQuery(sql).list();
		/**
		 * 应当是资源为key，权限为value。资源通常为url，权限就是那些以ROLE_为前缀的角色。一个资源可以由多个权限访问
		 */
		resourceMap = new HashMap<String,Collection<ConfigAttribute>>();
		for(String auth : query){
			
		ConfigAttribute ca = new SecurityConfig(auth);
		
		String sql1 = "select b.resource_string ";
			sql1 += "from Pub_Authorities_Resources a, Pub_Resource b, ";
			sql1 += "Pub_authorities c where a.resource_id = b.resource_id ";
			sql1 += "and a.authority_id = c.authority_id and c.Authority_name=";
			sql1 += username + "'";
			
		@SuppressWarnings("unchecked")
		List<String> query1 = session.createSQLQuery(sql1).list();
			for(String res : query1){
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
