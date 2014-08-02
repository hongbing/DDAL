/**
 * 
 */
package com.weibo.newbie.DAO;

import java.util.List;

import com.weibo.newbie.model.Status;



/**
 * @author hongbing
 *
 */
public interface StatusDao {
	
	public List<Status> queryUserStatusFromSlave(String uid, Integer page, Integer size);
	
	public Boolean InsertUserStatus2Master(String uid, String id); 
	
}
