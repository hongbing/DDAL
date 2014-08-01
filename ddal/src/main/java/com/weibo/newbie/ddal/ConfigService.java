/**
 * 
 */
package com.weibo.newbie.ddal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weibo.newbie.common.DBOperation;
import com.weibo.newbie.common.ShardingAlgorithm;
import com.weibo.newbie.model.ShardingServer;

/**
 * @author hongbing
 *	负责对数据库的DNS查找
 */
public class ConfigService {
	
	ShardingAlgorithm sAlgorithm = new ShardingAlgorithm();
	
	/**
	 * 
	 * getShardingInfoFromFile：从文件中获取到shardingServer的IP地址以及multi DB的端口号
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<ShardingServer> getShardingInfoFromFile() throws IOException {
		String s = null;
		List<ShardingServer> list = new ArrayList<ShardingServer>();
		File file = new File("shardingConfig");
		if (file.isFile() && file != null) {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			Map<String, Integer> map = new HashMap<String, Integer>();
			while( (s = bufferedReader.readLine()) != null) {
				ShardingServer ss = new ShardingServer();
				map.put(s.split(":")[0], Integer.valueOf(s.split(":")[1]));
				ss.setDBMap(map);
				list.add(ss);
			}
		}
		return list;
	}
	
	public ShardingServer getShardingServerByShardingNo(Integer no) {
		try {
			return this.getShardingInfoFromFile().get(no);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//通过用户ID计算当前微博应该存储在哪个db中
	public String getStatusStoreLoc(String uid, DBOperation op) {
		Integer shardingNo = sAlgorithm.computeShardByUserId(uid);
		if (shardingNo == null || shardingNo.intValue() < 0) {
			System.err.println("No available sharding no");
			return null;
		}
		ShardingServer shardingServer = getShardingServerByShardingNo(shardingNo);
		if (shardingServer == null) {
			System.err.println("can't find the sharding server");
			return null;
		}
		String hostname = null;
		Integer port = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		map = shardingServer.getDBMap();
		if (map != null && map.size() > 0) {
			if (op.equals(DBOperation.WRITE)) {
				hostname = (String) map.keySet().toArray()[6];
				System.out.println(hostname);
			} else {
				hostname = (String) map.keySet().toArray()[5];
			}
			port = map.get(hostname);
		}
		/**
		 * 到底使用哪个db,还需要重新设计
		 */
		return hostname + ":" + port + ":db" + Integer.valueOf(uid) % 4;
	}

	public static void main(String[] args) throws IOException {
		ConfigService configService = new ConfigService();
		String s = configService.getStatusStoreLoc("12453", DBOperation.WRITE);
		System.out.println(s);
	}
}
