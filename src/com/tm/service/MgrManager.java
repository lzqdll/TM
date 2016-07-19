package com.tm.service;

import java.util.List;

import com.tm.entity.ApplicationRecord;
import com.tm.entity.AttendType;
import com.tm.entity.User;

public interface MgrManager {

	boolean checkback(long appid, String checkresult);

	List<ApplicationRecord> listPendingApplications(long manager_id);

	boolean checkback(long appid, boolean checkresult, String type);

//	void recaculateLeavebalance(User user, AttendType attType);

}