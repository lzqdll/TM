package com.tm.serviceImpl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.tm.dao.AppRcdDAO;
import com.tm.dao.UserDAO;
import com.tm.entity.ApplicationRecord;
import com.tm.entity.LeaveBalance;
import com.tm.entity.User;
import com.tm.service.UserManager;
import com.tm.utils.LDAPAuthentication;

@Component
@Lazy
public class UserManagerImpl implements UserManager {
	private UserDAO userDao;
	LDAPAuthentication ldapAuthenticator;

	@Override
	public User validateUser(User user) {
		boolean result = false;
		User vUser = userDao.getByName(user.getUsrName());
		if (vUser != null)
			result = ldapAuthenticator.authenricate(user.getUsrName(), user.getUsrPassword());
		// List<User> userList = userDao.findbyUsernameAndPassword(user);
		if (result)
			return vUser;
		else
			return null;
	}

	// @Override
	// public Set<ApplicationRecord> findApplicationsbyusername(String username)
	// {
	// TODO Auto-generated method stub
	// User user = userDao.getByName(username);
	// return user.getApplicationRecord();
	// }

	@Override
	public List<LeaveBalance> findLeaveBalancebyusername(String username) {
		// TODO Auto-generated method stub
		User user = userDao.getByName(username);
		// System.out.println(user.getLeaveBalances().size());
		return user.getLeaveBalances();
	}

	@Resource
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	public void addorUpdateUser(User user) {
		// TODO Auto-generated method stub
		userDao.saveorupdate(user);
	}

	@Override
	public User getUserByName(String username) {
		return userDao.getByName(username);
	}

	@Override
	public List<User> findAllUsers() {
		return userDao.findALL();
	}

	@Resource
	public void setLdapAuthenticator(LDAPAuthentication ldapAuthenticator) {
		this.ldapAuthenticator = ldapAuthenticator;
	}
}
