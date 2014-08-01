/**
 * 
 */
package com.weibo.newbie.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;
import com.weibo.newbie.common.Constants;
import com.weibo.newbie.common.DBOperation;
import com.weibo.newbie.common.DateUtil;
import com.weibo.newbie.common.ShardingAlgorithm;
import com.weibo.newbie.ddal.ConfigService;

/**
 * @author hongbing
 *
 */
public class StatusDaoImpl implements StatusDao{

	ConfigService configService = new ConfigService();
	ShardingAlgorithm sAlgorithm = new ShardingAlgorithm();
	DBHelper dbHelper = new DBHelper();
	
	public List<String> queryUserStatusFromSlave(String uid, Integer page, Integer size) {
		List<String> userStatusIDList = new ArrayList<String>();
		Integer start_status = (page - 1) * Constants.STATUS_COUNT_PERPAGE;
		Integer end_status = start_status + size;
		//通过二级索引查询请求的用户的微博所发表的月份,通过月份避免全表查询
		//表名：USER_STATUS_COUNT_PERMONTH
		
		List<String> months= new ArrayList<String>();
		ResultSet resultSet = null;
		String sql1 = "select * from %s where uid = '%s' order by %s desc;";
//		String sql1 = "select * from " + Constants.USER_STATUS_COUNT_PERMONTH + " where uid = " 
//				+ "'" + uid + "'" + " order by " + Constants.COLUMN_NAME_OF_MONTH + " desc;";
		String dBStr = configService.getStatusStoreLoc(uid, DBOperation.READ);
		String ip = dBStr.split(":")[0];
		String port = dBStr.split(":")[1];
		String dbName = dBStr.split(":")[2];
//		PreparedStatement pst1 = (PreparedStatement) dbHelper.getStatement(sql1, ip, port);
		PreparedStatement pst1 = (PreparedStatement) dbHelper.getStatement(
				String.format(sql1, Constants.USER_STATUS_COUNT_PERMONTH, uid, Constants.COLUMN_NAME_OF_MONTH), ip, port,dbName);
		try {
			resultSet = pst1.executeQuery();
			Integer sum = 0;
			while(resultSet.next()) {
				sum += Integer.parseInt(resultSet.getString(Constants.COLUMN_NAME_OF_COUNT));
				months.add(resultSet.getString(Constants.COLUMN_NAME_OF_MONTH));
				if (sum >= end_status) {
					break;
				}
				if (months.size() >= 12) {
					break;
				}
			}
			resultSet = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String s = "";
		for (int i = 0; i < months.size(); i++) {
			s = s + months.get(i) + (i == months.size() -1 ? " " : ", ");
		}
		//查询user_status表
//		String sql2 = "select * from " + Constants.USER_STATUS 
//				+ " where " + Constants.COLUMN_NAME_OF_MONTH 
//				+ " in (" + s + ");";
		String sql2 = "select * from %s where %s in ( %s );";
		//获取用户微博发表的微博ID
//		PreparedStatement pst2 = (PreparedStatement) dbHelper.getStatement(
//				sql2, ip, port);
		PreparedStatement pst2 = (PreparedStatement) dbHelper.getStatement(
				String.format(sql2, Constants.USER_STATUS, Constants.COLUMN_NAME_OF_MONTH, s), ip, port, dbName);
		try {
			resultSet = pst2.executeQuery();
			while(resultSet.next()) {
				userStatusIDList.add(resultSet.getString(Constants.COLUMN_NAME_OF_STATUS_ID));
			}
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userStatusIDList;
	}
	
	/**
	 * 
	 * InsertUserStatus：mysql数据库分为主库和从库，主库负责写，从库负责读，主库和从库保持数据更新
	 * 			首先写入到主库，同时更新二级索引和从库，从库的更新由mysql自己处理
	 * 			当前只支持一条插入，应该支持批量数据插入
	 * @param uid
	 * @param id
	 * @return
	 */
	public Boolean InsertUserStatus2Master(String uid, String sid) {
		String dBStr = configService.getStatusStoreLoc(uid, DBOperation.WRITE);
		if (dBStr == null) {
			return false;
		}
		String ip = dBStr.split(":")[0];
		String port = dBStr.split(":")[1];
		String dbName = dBStr.split(":")[2];
		String sql1 = "insert into %s (%s, %s) values('%s', '%s');";
		PreparedStatement pst = (PreparedStatement) dbHelper.getStatement(
				String.format(sql1, Constants.USER_STATUS, Constants.COLUMN_NAME_OF_USER_ID,
						Constants.COLUMN_NAME_OF_STATUS_ID, uid, sid), ip, port, dbName);
		try {
			Boolean b1 = pst.execute();
			if (false == b1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.close();
		}
		String currentMonth = DateUtil.getCurrentMonth();
		//更新二级索引
		String sql2 = "select * from  %s where uid = '%s' and month = '%s';";
		pst = (PreparedStatement) dbHelper.getStatement(
				String.format(sql2, Constants.USER_STATUS_COUNT_PERMONTH, uid, currentMonth), ip, port, dbName);
		Integer currentCount = 0;
		try {
			ResultSet resultSet = pst.executeQuery();
			currentCount = Integer.valueOf(resultSet.getString(Constants.COLUMN_NAME_OF_COUNT));
			currentCount++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql3 = "update %s set count = '%s' where uid = '%s';";
		try {
			dbHelper.getStatement(String.format(sql3, Constants.USER_STATUS_COUNT_PERMONTH,
					currentCount, uid), ip, port, dbName).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.close();
		}
		
		return true;
	}
	
	
}
