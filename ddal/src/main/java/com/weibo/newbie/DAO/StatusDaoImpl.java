/**
 * 
 */
package com.weibo.newbie.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weibo.newbie.common.Constants;
import com.weibo.newbie.common.ShardingAlgorithm;
import com.weibo.newbie.ddal.ConfigService;
import com.weibo.newbie.model.ShardingServer;

/**
 * @author hongbing
 *
 */
public class StatusDaoImpl implements StatusDao{

	ConfigService configService = new ConfigService();
	ShardingAlgorithm sAlgorithm = new ShardingAlgorithm();
	DBHelper dbHelper = null;
	
	public List<String> queryUserStatusFromSlave(String uid, Integer page, Integer size) {
		Integer shardingNo = sAlgorithm.computeShardByUserId(uid);
		ShardingServer shardingServer = configService.getShardingServerByShardingNo(shardingNo);
		String hostname = null;
		List<String> ports = null;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map = shardingServer.getMultiDBMap();
		if (map != null && map.size() > 0) {
			hostname = (String) map.keySet().toArray()[0];
			ports = map.get(hostname);
		}
		
		Integer start_status = (page - 1) * Constants.STATUS_COUNT_PERPAGE;
		Integer end_status = start_status + size;
		//通过二级索引查询请求的用户的微博所发表的月份,通过月份避免全表查询
		//表名：USER_STATUS_COUNT_PERMONTH
		
		List<String> months= new ArrayList<String>();
		ResultSet resultSet = null;
		String sql1 = "select * from " + Constants.USER_STATUS_COUNT_PERMONTH + "where uid = " 
				+ "'" + uid + "'" + "order by " + Constants.COLUMN_NAME_OF_MONTH + "desc";
		//到底用哪个db，还需重新考虑
		dbHelper = new DBHelper(sql1, hostname, ports.get(0), "db0");
		try {
			resultSet = dbHelper.pst.executeQuery();
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//查询user_status表
		String sql2 = "select *";
		dbHelper = new DBHelper(sql2, hostname, ports.get(0), "");
		return null;
	}
	
	/**
	 * 
	 * InsertUserStatus：mysql数据库分为主库和从库，主库负责写，从库负责读，主库和从库保持数据更新
	 * 			首先写入到主库，同时更新二级索引和从库。
	 * 
	 * @param uid
	 * @param id
	 * @return
	 */
	public Boolean InsertUserStatus2Master(String uid, String id) {
		
		return null;
	}
	
}
