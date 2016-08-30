package com.chinahanjiang.crm.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static final SimpleDateFormat shortSdf = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final SimpleDateFormat shortSdf2= new SimpleDateFormat(
			"yyyyMMdd");
	private static final SimpleDateFormat longSdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 获得本天的开始时间，即2012-01-01 00:00:00
	 *
	 * @return
	 */
	public static Timestamp getCurrentDayStartTime() {
		Date now = new Date();
		try {
			now = shortSdf.parse(shortSdf.format(now));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return covertDateToTimestamp(now);
	}
	
	/**
	 * 获得本天的结束时间，即2012-01-01 23:59:59
	 *
	 * @return
	 */
	public static Timestamp getCurrentDayEndTime() {
		Date now = new Date();
		try {
			now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return covertDateToTimestamp(now);
	}

	private static Timestamp covertDateToTimestamp(Date date) {

		Timestamp t;

		String dateStr = "";
		try {

			dateStr = longSdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		t = Timestamp.valueOf(dateStr);

		return t;
	}
	
	public static String getCurrentDayString(){
		
		Date now = new Date();
		String dateStr = "";
		dateStr = shortSdf2.format(now);
		return dateStr;
	}
}
