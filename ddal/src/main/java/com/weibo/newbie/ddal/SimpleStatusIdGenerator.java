/**
 * 
 */
package com.weibo.newbie.ddal;


/**
 * 仅仅以当前UNIX时间戳作为微博ID
 * @author hongbing
 *
 */
public class SimpleStatusIdGenerator implements StatusIdGenerator {

	/* (non-Javadoc)
	 * @see com.weibo.newbie.ddal.StatusIdGenerator#generateStatusId()
	 */
	@Override
	public String generateStatusId() {
		return String.valueOf(System.currentTimeMillis());
	}

}
