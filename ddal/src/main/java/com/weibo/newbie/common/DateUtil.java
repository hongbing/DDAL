package com.weibo.newbie.common;

import java.util.Calendar;

public class DateUtil {

	/**
	 * get month like:201407
	 * @return
	 */
	public static String getCurrentYearAndMonth() {
		Calendar cal = Calendar.getInstance();
		Integer year = cal.get(Calendar.YEAR);
		Integer month = cal.get(Calendar.MONTH) + 1;
		return year.toString() + (month >= 10 ? month.toString() : ("0" + month));
	}
	
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getCurrentYearAndMonth());
	}
}
