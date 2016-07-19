package com.tm.service;

import com.tm.entity.AttendType;
import com.tm.entity.User;

public interface LeaveBalanceMgr {
	boolean refreshLeavebalance(User user, AttendType attType);
	boolean refreshLeavebalance();
}
