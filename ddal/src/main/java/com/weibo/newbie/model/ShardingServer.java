
 /**
 * 
 */
package com.weibo.newbie.model;

import java.util.List;
import java.util.Map;


 /**
 * @Classname：com.weibo.newbie.model.ShardingServer 
 * @Description：
 *
 * @Author BING
 * @Date：2014-7-30
 *
 * @Copyright：Copyright (c) 2014
 */
public class ShardingServer {
	
	private String shardingServerName;
	/**
	 * string -->IP Address
	 * List<String> --> DB port
	 */
	private Map<String, List<String>> multiDBMap;
	
	public String getShardingServerName() {
		return shardingServerName;
	}
	public void setShardingServerName(String shardingServerName) {
		this.shardingServerName = shardingServerName;
	}
	public Map<String, List<String>> getMultiDBMap() {
		return multiDBMap;
	}
	public void setMultiDBMap(Map<String, List<String>> multiDBMap) {
		this.multiDBMap = multiDBMap;
	}
	
	
}
