
 /**
 * 
 */
package com.weibo.newbie.model;

import java.util.List;


 /**
 * @Classname：com.weibo.newbie.model.User 
 * @Description：
 *
 * @Author BING
 * @Date：2014-7-30
 *
 * @Copyright：Copyright (c) 2014
 */
public class User {
	
	private String userId;
	private String userName;
	private List<String> status;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<String> getStatus() {
		return status;
	}
	public void setStatus(List<String> status) {
		this.status = status;
	}
	
	
}
