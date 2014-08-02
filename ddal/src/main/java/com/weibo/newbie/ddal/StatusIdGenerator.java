/**
 * 
 */
package com.weibo.newbie.ddal;


/**
 * @author hongbing
 * 微博ID生成器
 */
public interface StatusIdGenerator {

	/**
	 * 
	 * @return 产生全局唯一的微博ID
	 */
	String generateStatusId();
}
