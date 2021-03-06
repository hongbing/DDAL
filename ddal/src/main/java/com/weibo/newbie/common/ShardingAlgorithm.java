/**
 * 
 */
package com.weibo.newbie.common;

import java.util.Random;
import java.util.zip.CRC32;

/**
 * @author hongbing
 *
 */
public class ShardingAlgorithm {
	
	private Integer shardingCount = 4;
	//expand sharding number
	private volatile Boolean expand = false;
	//shrinking sharding number
	private volatile Boolean shrink = false;
	
	/**
	 *  compute the sharding no by user id
	 */
	public Integer computeShardByUserId(String uid) {
//		CRC32 crc32 = new CRC32();
//		crc32.update(uid.getBytes());
//		Integer sh = (int) (crc32.getValue() % getShardingCount());
//		return sh < 0 ? sh + getShardingCount() : sh;
		return new Integer(0);
	}

	public Boolean getExpand() {
		return expand;
	}

	public void setExpand(Boolean expand) {
		this.expand = expand;
	}

	public Integer getShardingCount() {
		return shardingCount;
	}

	public void setShardingCount(Integer shardingCount) {
		this.shardingCount = shardingCount;
	}

	public Boolean getShrink() {
		return shrink;
	}

	public void setShrink(Boolean shrink) {
		this.shrink = shrink;
	}

	public static void main(String[] args) {
		ShardingAlgorithm shardingAlgorithm = new ShardingAlgorithm();
		Integer []count = new Integer[4];
		for (int i = 0; i < count.length; i++) {
			count[i] = 0;
		}
		for (int i = 0; i < 200000; i++) {
			int n = shardingAlgorithm.computeShardByUserId(String.valueOf(new Random().nextInt()));
			count[n]++;
		}
		for (int i = 0; i < count.length; i++) {
			System.out.println("count "+ i +" :" + count[i]);
		}
	}

}
