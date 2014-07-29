/**
 * 
 */
package com.weibo.newbie.DAO;

import java.util.List;

import com.weibo.newbie.common.ShardingAlgorithm;
import com.weibo.newbie.ddal.ConfigService;

/**
 * @author hongbing
 *
 */
public class StatusDaoImpl implements StatusDao{

	ConfigService configService = new ConfigService();
	ShardingAlgorithm sAlgorithm = new ShardingAlgorithm();
	
	public List<String> queryUserStatus(String uid, Integer page, Integer size) {
		String []mouth = null;
		//通过二级缓存查询请求的用户的微博所发表的月份
		
		//通过月份避免全表查询
		Integer shardingNo = sAlgorithm.computeShardByUserId(uid);
		
		
		return null;
	}
	
}
