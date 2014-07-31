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

/**
 * @author hongbing
 *
 */
public class StatusService {

	ConfigService configService = new ConfigService();
	StatusDao statusDao = new StatusDaoImpl();
	
	
	/**
	 * get user's first page , 20 status
	 * @param uid
	 * @return
	 */
	public List<String> getUserStatus(String uid) {
		if (null == uid || uid.trim().equals("")) {
			return null;
		}			
		return statusDao.queryUserStatusFromSlave(uid, Constants.DEFAULT_PAGE, Constants.DEFAULT_SIZE);
	}
	
	/**
	 * get user status 
	 * @param uid
	 * @param page 
	 * @param size 
	 * @return
	 */
	public List<String> getUserStatus(String uid, Integer page, Integer size) {
		return statusDao.queryUserStatusFromSlave(uid, page, size);
	}
	
	/**
	 * 
	 * @param uids multiple user id
	 * @param page
	 * @param size
	 * @return
	 */
	public Map<String, List<String>> getUsersStatus(String [] uids,Integer page, Integer size) {
		Map<String, List<String>> usersStatusMap = new HashMap<String, List<String>>();
		for (String uid : uids) {
			usersStatusMap.put(uid, statusDao.queryUserStatusFromSlave(uid, page, size));
		}
		return usersStatusMap;
	}

	/**
	 * store status 
	 * @param uid
	 * @param sid
	 * @return
	 */
	public Boolean storeStatus(String uid, String sid) {
		return statusDao.InsertUserStatus2Master(uid, sid);
	}

}
