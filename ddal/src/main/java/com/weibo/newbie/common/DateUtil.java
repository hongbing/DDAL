package com.weibo.newbie.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	/**
	 * 将格式为YYYY/MM/DD HH:MM:SS的微博元年（1986/01/01 00:00:01）时间转化为Unix 时间戳
	 * @return
	 */
	public static Long getWeiboEpochMilliseconds() {
		
		try {
			Long weiboEpochMilliseconds = new SimpleDateFormat("YYYY/MM/DD HH:MM:SS")
										.parse(Constants.WEIBO_EPOCH_YMD).getTime();
			return weiboEpochMilliseconds;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getCurrentYearAndMonth());
	}
}
