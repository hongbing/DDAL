package com.weibo.newbie.ddal;

import java.util.List;


public class ddalTest {

	public static void main(String[] args) {
		StatusService service = new StatusServiceImpl();
		String uid = "1123";
		String sid = "1234564989";
		Boolean boolean1 = service.storeStatus(uid, sid);
		List<String> statusId = null;
		if (boolean1 ==null || !boolean1) {
			System.out.println("store status failed");
			return ;
		}
		statusId = service.getUserStatus(uid);
		for (String s : statusId) {
			System.out.println("statusId:" + s);
		}
	}

}
