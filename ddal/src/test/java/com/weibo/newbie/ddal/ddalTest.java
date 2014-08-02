package com.weibo.newbie.ddal;

import java.util.List;

import com.weibo.newbie.model.Status;


public class ddalTest {

	public static void main(String[] args) {
		StatusService service = new StatusServiceImpl();
		String uid = "1124";
		String content = uid + " write a new weibo";
		Boolean boolean1 = service.storeStatus(uid, content);
		List<Status> statusList = null;
		if (boolean1 ==null || !boolean1) {
			System.out.println("store status failed");
			return ;
		}
		statusList = service.getUserStatus(uid);
		for (Status s : statusList) {
			System.out.println("Status User: " + s.getUser().getUserId() + "  "
					+ "Status ID: " + s.getStatusId() + "  "
					+ "Status content: " + s.getContent());
		}
	}

}
