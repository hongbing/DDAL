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
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			while( (s = bufferedReader.readLine()) != null) {
				ShardingServer ss = new ShardingServer();
				ss.setShardingServerName(s.split(":")[0]);
				map.put(s.split(":")[1], Arrays.asList(s.split(":")[2]));
				ss.setMultiDBMap(map);
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
	public String getStatusStoreLoc(String uid) {
		Integer shardingNo = sAlgorithm.computeShardByUserId(uid);
		ShardingServer shardingServer = getShardingServerByShardingNo(shardingNo);
		String hostname = null;
		List<String> ports = null;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map = shardingServer.getMultiDBMap();
		if (map != null && map.size() > 0) {
			hostname = (String) map.keySet().toArray()[0];
			ports = map.get(hostname);
		}
		/**
		 * 到底使用哪个db，算法怎么设计的还需重新考虑,一个端口会对应一个db
		 */
		return hostname + ":" + ports.get(0);
	}

	public static void main(String[] args) throws IOException {
		ConfigService configService = new ConfigService();
		configService.getShardingInfoFromFile();
	}
}
