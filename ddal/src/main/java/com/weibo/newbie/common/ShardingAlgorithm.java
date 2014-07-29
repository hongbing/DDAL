/**
 * 
 */
package com.weibo.newbie.common;

import java.util.zip.CRC32;

/**
 * @author hongbing
 *
 */
public class ShardingAlgorithm {
	
	private Integer shardingCount = 4;
	
	
	public Integer computeShardByUserId(String uid) {
		CRC32 crc32 = new CRC32();
		crc32.update(uid.getBytes());
		return (int) crc32.getValue() % shardingCount;
	}


	public Integer getShardingCount() {
		return shardingCount;
	}


	public void setShardingCount(Integer shardingCount) {
		this.shardingCount = shardingCount;
	}



}
