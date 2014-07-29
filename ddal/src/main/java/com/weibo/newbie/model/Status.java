
 /**
 * 
 */
package com.weibo.newbie.model;


 /**
 * @Classname：com.weibo.newbie.model.Status 
 * @Description：
 *
 * @Author BING
 * @Date：2014-7-30
 *
 * @Copyright：Copyright (c) 2014
 */
public class Status {

	private String statusId;
	private User user;
	private String content;
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
