/**
 * 
 */
package com.weibo.newbie.ddal;

import java.util.List;
import java.util.Map;

import com.weibo.newbie.model.Status;

/**
 * @author hongbing
 *
 */
public interface StatusService {

	/**
	 * 以默认page和size返回用户发表的微博信息
	 * @param uid
	 * @return
	 */
	List<Status> getUserStatus(String uid);
	
	/**
	 * 
	 * @param uid
	 * @param page
	 * @param size
	 * @return
	 */
	List<Status> getUserStatus(String uid, Integer page, Integer size);
	
	/**
	 * 
	 * @param uids 多个用户的id
	 * @param page
	 * @param size
	 * @return
	 */
	Map<String, List<Status>> getUsersStatus(String [] uids,Integer page, Integer size);
	
	/**
	 * 保存用户微博信息
	 * @param uid
	 * @param content 微博内容
	 * @return
	 */
	Boolean storeStatus(String uid, String content);
}
