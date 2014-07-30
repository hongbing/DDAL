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
	
	public List<String> queryUserStatusFromSlave(String uid, Integer page, Integer size);
	
	public Boolean InsertUserStatus2Master(String uid, String id); 
	
}
