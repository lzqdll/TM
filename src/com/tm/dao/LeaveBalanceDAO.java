package com.tm.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.tm.entity.LeaveBalance;
import com.tm.entity.User;

public interface LeaveBalanceDAO {
	public LeaveBalance get(int id);

	public void saveorupdate(LeaveBalance leaveBalance);

	public void delete(LeaveBalance leaveBalance);

	public List<LeaveBalance> findALL();

	public List<LeaveBalance> findByUser(User user);

	List getLeaveBalancebyCriteria(DetachedCriteria criteria);
}
