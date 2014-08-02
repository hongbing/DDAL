/**
 * 
 */
package com.weibo.newbie.ddal;

import com.weibo.newbie.common.DateUtil;

/**
 * 微博ID格式为：30位时间戳+4位IDC号+15位循环码+2位业务号+1位分发机器码
 * @author hongbing
 *
 */
public class CustomStatusIdGenerator implements StatusIdGenerator {

	/* (non-Javadoc)
	 * @see com.weibo.newbie.ddal.StatusIdGenerator#generateStatusId()
	 */
	@Override
	public String generateStatusId() {
		String statusId = null;
		Long currenMiliseconds = System.currentTimeMillis();
		Long epochMiliseconds = DateUtil.getWeiboEpochMilliseconds();
		//
		return null;
	}

}
