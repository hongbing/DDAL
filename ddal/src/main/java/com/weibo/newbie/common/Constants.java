/**
 * 
 */
package com.weibo.newbie.common;



/**
 * @author hongbing
 *
 */
public class Constants {

	public static final Integer DEFAULT_PAGE = 1;
	public static final Integer DEFAULT_SIZE = 20;
	
	//db prefix
	public static final String PREFIX_DB = "db";
	//table prefix
	public static final String PREFIX_OF_USER_STATUS_TABLE = "US_";
	//table name
	public static final String TABLE_USER_STATUS_COUNT_PERMONTH = PREFIX_OF_USER_STATUS_TABLE + "COUNT_PM";
	//类似US_201408
	public static final String TABLE_USER_STATUS_4_CURRENT_MONTH = PREFIX_OF_USER_STATUS_TABLE + DateUtil.getCurrentYearAndMonth();
	
	//US_COUNT_PM count column name
	public static final String COLUMN_NAME_OF_COUNT = "count";
	public static final String COLUMN_NAME_OF_MONTH = "month";
	
	//USER_STATUS_4_CURRENT_MONTH column name
	public static final String COLUMN_NAME_OF_USER_ID = "uid";
	public static final String COLUMN_NAME_OF_STATUS_ID = "sid";
	public static final String COLUMN_NAME_OF_CONTENT = "content";
	
	
	public static final Integer DB_COUNT = 4;
	
	//status count per page
	public static final Integer STATUS_COUNT_PERPAGE = 30;
	
	//weibo epoch
	public static final String WEIBO_EPOCH_YMD = "1986/01/01 00:00:01";
}
