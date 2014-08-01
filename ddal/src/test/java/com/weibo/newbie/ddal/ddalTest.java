package com.weibo.newbie.ddal;

import java.util.List;


public class ddalTest {

	public static void main(String[] args) {
		StatusService service = new StatusServiceImpl();
		String uid = "1123";
		String sid = "123456789";
		Boolean boolean1 = service.storeStatus(uid, sid);
		List<String> statusId = null;
		if (!boolean1) {
			System.out.println("store status failed");
		} else {
			statusId = service.getUserStatus(uid);
		}
		for (String s : statusId) {
			System.out.println("statusId:" + s);
		}
	}

}
