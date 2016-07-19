package com.tm.service;

import java.util.List;
import java.util.Set;

import com.tm.entity.ApplicationRecord;
import com.tm.entity.LeaveBalance;
import com.tm.entity.User;

public interface UserManager {

	User validateUser(User user);

	User getUserByName(String username);

	void addorUpdateUser(User user);

	List<User> findAllUsers();

	List<LeaveBalance> findLeaveBalancebyusername(String username);

}