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
import com.weibo.newbie.ddal.SimpleStatusIdGenerator;
import com.weibo.newbie.ddal.StatusIdGenerator;
import com.weibo.newbie.model.Status;
import com.weibo.newbie.model.User;

/**
 * @author hongbing
 *
 */
public class StatusDaoImpl implements StatusDao{

	ConfigService configService = new ConfigService();
	ShardingAlgorithm sAlgorithm = new ShardingAlgorithm();
	DBHelper dbHelper = new DBHelper();
	StatusIdGenerator statusIdGenerator = new SimpleStatusIdGenerator();
	
	public List<Status> queryUserStatusFromSlave(String uid, Integer page, Integer size) {
		List<Status> userStatusIDList = new ArrayList<Status>();
		Integer start_status = (page - 1) * Constants.STATUS_COUNT_PERPAGE;
		Integer end_status = start_status + size;
		//通过二级索引查询请求的用户的微博所发表的月份,通过月份避免全表查询
		//表名：USER_STATUS_COUNT_PERMONTH
		List<String> months= new ArrayList<String>();
		ResultSet resultSet = null;
		String sql1 = "select * from %s where uid = '%s' order by %s desc;";
		String dBStr = configService.getStatusStoreLoc(uid, DBOperation.READ);
		String ip = dBStr.split(":")[0];
		String port = dBStr.split(":")[1];
		String dbName = dBStr.split(":")[2];
		PreparedStatement pst1 = (PreparedStatement) dbHelper.getStatement(
				String.format(sql1, Constants.TABLE_USER_STATUS_COUNT_PERMONTH, uid, Constants.COLUMN_NAME_OF_MONTH), ip, port,dbName);
		try {
			resultSet = pst1.executeQuery();
			Integer sum = 0;
			if (resultSet != null) {
				while(resultSet.next()) {
					sum += Integer.parseInt(resultSet.getString(Constants.COLUMN_NAME_OF_COUNT));
					months.add(resultSet.getString(Constants.COLUMN_NAME_OF_MONTH));
					if (sum >= end_status) {
						break;
					}
					if (months.size() >= 12 || months.size() <= 0) {
						break;
					}
				}
			} else {// no month results
				return null;
			}
			
			resultSet = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//查询user status表
		for (int i = 0; i < months.size(); i++) {
			String sql2 = "select * from "+ Constants.PREFIX_OF_USER_STATUS_TABLE + months.get(i)
					+ " where uid =" + uid + ";";
			System.out.println(sql2 + "on " + dbName);
			//获取用户微博发表的微博ID
			PreparedStatement pst2 = (PreparedStatement) dbHelper.getStatement(sql2, ip, port, dbName);
			try {
				resultSet = pst2.executeQuery();
				while(resultSet.next()) {
					Status status = new Status();
					status.setStatusId(resultSet.getString(Constants.COLUMN_NAME_OF_STATUS_ID));
					status.setContent(resultSet.getString(Constants.COLUMN_NAME_OF_CONTENT));
					status.setUser(new User(uid));
					userStatusIDList.add(status);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		dbHelper.close();
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
	public Boolean InsertUserStatus2Master(String uid, String content) {
		String dBStr = configService.getStatusStoreLoc(uid, DBOperation.WRITE);
		if (dBStr == null) {
			return false;
		}
		String ip = dBStr.split(":")[0];
		String port = dBStr.split(":")[1];
		String dbName = dBStr.split(":")[2];
		//根据月份判断应该将微博写入到哪张表中，通过二级索引表查看是否存在当月的用户微博表。
		String sid = statusIdGenerator.generateStatusId();
		if (sid == null) {
			System.out.println("generate status id failed");
			return false;
		}
		String sql1 = "insert into %s (%s, %s, %s) values('%s', '%s', '%s');";
		PreparedStatement pst = (PreparedStatement) dbHelper.getStatement(
				String.format(sql1, Constants.TABLE_USER_STATUS_4_CURRENT_MONTH, Constants.COLUMN_NAME_OF_USER_ID, 
						Constants.COLUMN_NAME_OF_STATUS_ID, Constants.COLUMN_NAME_OF_CONTENT, uid, sid, content), 
						ip, port, dbName);
		try {
			System.out.println(String.format(sql1, Constants.TABLE_USER_STATUS_4_CURRENT_MONTH, Constants.COLUMN_NAME_OF_USER_ID, 
					Constants.COLUMN_NAME_OF_STATUS_ID, Constants.COLUMN_NAME_OF_CONTENT, uid, sid, content) + "on " + dbName);
			if (pst == null) {
				return null;
			}
			pst.execute();
			//更新二级索引表
			String sql3 = "insert into %s set uid = %s, month = %s, count = 1 on duplicate key update count =  count + 1;";
			pst = (PreparedStatement) dbHelper.getStatement(String.format(sql3, Constants.TABLE_USER_STATUS_COUNT_PERMONTH,
					uid, DateUtil.getCurrentYearAndMonth()), ip, port, dbName);
			if (pst == null) {
				return null;
			}
			pst.executeUpdate();
			System.out.println(String.format(sql3, Constants.TABLE_USER_STATUS_COUNT_PERMONTH,uid, DateUtil.getCurrentYearAndMonth()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.close();
		}
		
		return true;
	}
	
	
//	public Boolean createCurrentMonthTable() {
//		String month = DateUtil.getCurrentYearAndMonth();
//		String sql = "create table " + Constants.PREFIX_OF_USER_STATUS_TABLE + month + "("
//				+ "id int primary key not null auto_increment," 
//				+ "uid int not null, "
//				+ "sid bigint" 
//				+ ");";
//		return false;
//	}
	
}
