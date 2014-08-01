/**
 * 
 */
package com.weibo.newbie.ddal;

import java.util.List;
import java.util.Map;

/**
 * @author hongbing
 *
 */
public interface StatusService {

	List<String> getUserStatus(String uid);
	
	List<String> getUserStatus(String uid, Integer page, Integer size);
	
	Map<String, List<String>> getUsersStatus(String [] uids,Integer page, Integer size);
	
	Boolean storeStatus(String uid, String sid);
}
