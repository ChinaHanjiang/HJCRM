package com.chinahanjiang.crm.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
/**
 * 
 */
public class UserSession{
	
	private static Logger logger = Logger.getLogger(UserSession.class);
	
	protected final static ThreadLocal<Map<String,Object>> threadContext = new MapThreadLocal();
	
	private UserSession(){};
	
	public static void put(String key,Object value){
		getContextMap().put(key,value);
	}
	
	public static Object remove(String key){
		return getContextMap().remove(key);
	}
	
	public static Object get(String key){
		return getContextMap().get(key);
	}
	
	private static class MapThreadLocal extends ThreadLocal<Map<String,Object>> {
        protected Map<String,Object> initialValue() {
        	return new HashMap<String,Object>() {
				
        		private static final long serialVersionUID = 3637958959138295593L;
				
				public Object put(String key, Object value) {
                    if (logger.isDebugEnabled()) {
                        if (containsKey(key)) {
                        	logger.debug("Overwritten attribute to thread context: " + key
                                + " = " + value);
                        } else {
                        	logger.debug("Added attribute to thread context: " + key + " = "
                                + value);
                        }
                    }

                    return super.put(key, value);
                }
            };
        }
    }
	
	/**
     * 取得thread context Map的实例。
     *
     * @return thread context Map的实例
     */
    protected static Map<String,Object> getContextMap() {
        return (Map<String,Object>) threadContext.get();
    }
	
    
    /**
     * 清理线程所有被hold住的对象。以便重用！
     */
    
    public static void reset(){
    	getContextMap().clear();
    }
}