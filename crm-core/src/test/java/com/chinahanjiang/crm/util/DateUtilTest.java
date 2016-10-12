package com.chinahanjiang.crm.util;

import org.junit.Test;

public class DateUtilTest {

	@Test
	public void testBegin(){
		
		System.out.println(DateUtil.getCurrentDayStartTime());
	}
	
	@Test
	public void testGetCurrentDayString(){
		
		System.out.println(DateUtil.getCurrentDayString());
	}
}
