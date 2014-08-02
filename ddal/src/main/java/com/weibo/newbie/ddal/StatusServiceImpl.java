/**
 * 
 */
package com.weibo.newbie.ddal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weibo.newbie.DAO.StatusDao;
import com.weibo.newbie.DAO.StatusDaoImpl;
import com.weibo.newbie.common.Constants;
import com.weibo.newbie.model.Status;

/**
 * @author hongbing
 *
 */
public class StatusServiceImpl implements StatusService{

	StatusDao statusDao = new StatusDaoImpl();
	
	
	/**
	 * get user's first page , 20 status
	 * @param uid
	 * @return
	 */
	public List<Status> getUserStatus(String uid) {
		if (null == uid || uid.trim().equals("")) {
			return null;
		}			
		return statusDao.queryUserStatusFromSlave(uid, Constants.DEFAULT_PAGE, Constants.DEFAULT_SIZE);
	}
	
	public List<Status> getUserStatus(String uid, Integer page, Integer size) {
		return statusDao.queryUserStatusFromSlave(uid, page, size);
	}
	
	public Map<String, List<Status>> getUsersStatus(String [] uids,Integer page, Integer size) {
		Map<String, List<Status>> usersStatusMap = new HashMap<String, List<Status>>();
		for (String uid : uids) {
			usersStatusMap.put(uid, statusDao.queryUserStatusFromSlave(uid, page, size));
		}
		return usersStatusMap;
	}

	public Boolean storeStatus(String uid, String content) {
		return statusDao.InsertUserStatus2Master(uid, content);
	}

}
