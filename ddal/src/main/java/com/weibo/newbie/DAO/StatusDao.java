/**
 * 
 */
package com.weibo.newbie.DAO;

import java.util.List;



/**
 * @author hongbing
 *
 */
public interface StatusDao {
	
	public List<String> queryUserStatus(String uid, Integer page, Integer size);
	
}
