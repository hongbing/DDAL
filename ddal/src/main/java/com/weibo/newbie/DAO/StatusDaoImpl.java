/**
 * 
 */
package com.weibo.newbie.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public List<String> queryUserStatus(String uid, Integer page, Integer size) {
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
		
		//通过二级索引查询请求的用户的微博所发表的月份,通过月份避免全表查询,二级索引表存储在一个shard_0的db0中
		//表名：User_Mouth_Status_Count
		String tableName = "User_Mouth_Status_Count";
		String []mouths = null;
		ResultSet resultSet = null;
		String sql1 = "select * from " + tableName + "where uid = " 
				+ "'" + uid + "'";
		dbHelper = new DBHelper(sql1, hostname, ports.get(0), "db0");
		try {
			resultSet = dbHelper.pst.executeQuery();
			while(resultSet.next()) {
				//
				//
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql2 = "select *";
		dbHelper = new DBHelper(sql2, hostname, ports.get(0), "");
		return null;
	}
	
	/**
	 * 
	 * InsertUserStatus：mysql数据库分为主库和从库，主库负责写，从库负责读，主库和从库保持数据更新
	 * 			首先写入到主库，然后更新二级索引和从库。
	 * 
	 * @param uid
	 * @param id
	 * @return
	 */
	public Boolean InsertUserStatus(String uid, String id) {
		
		return null;
	}
	
}
