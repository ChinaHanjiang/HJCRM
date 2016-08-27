package com.chinahanjiang.crm.util;

import org.junit.Test;

public class MD5UtilTest {

	@Test
	public void testPassword(){
		
		String pwd = MD5Util.MD5("123456");
		System.out.println(pwd);
	}
	
}
