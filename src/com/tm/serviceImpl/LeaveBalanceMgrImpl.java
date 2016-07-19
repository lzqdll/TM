package com.tm.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.tm.dao.AppRcdDAO;
import com.tm.dao.LeaveBalanceDAO;
import com.tm.dao.VacationDAO;
import com.tm.entity.AttendType;
import com.tm.entity.LeaveBalance;
import com.tm.entity.User;
import com.tm.entity.VacationRecord;
import com.tm.service.LeaveBalanceMgr;
import com.tm.service.UserManager;

@Component
@Lazy
public class LeaveBalanceMgrImpl implements LeaveBalanceMgr {
	private LeaveBalanceDAO leaveBalanceDao;
	private VacationDAO vrDao;
	private UserManager um;

	@Override
	public boolean refreshLeavebalance(User user, AttendType attType) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(LeaveBalance.class).add(Restrictions.eq("user", user))
				.add(Restrictions.eq("attType", attType));
		List<LeaveBalance> lBalances = leaveBalanceDao.getLeaveBalancebyCriteria(dc);
		String hql = " AND attType=? AND expiredTime>? AND status='" + AppRcdDAO.APPROVAL + "'";
		List<VacationRecord> vrs = vrDao.findRecordsByUser(new Object[] { user, attType, new Date() }, hql);
		float sum = 0;
		for (int i = 0; i < vrs.size(); i++) {
			sum += vrs.get(i).getAdjustedduration();
		}
		LeaveBalance lb;
		if (lBalances.size() > 0) {
			lb = lBalances.get(0);
			lb.setHours(sum);
		} else {
			lb = new LeaveBalance();
			lb.setHours(sum);
			lb.setAttType(attType);
			lb.setUser(user);
		}
		System.out.println(lBalances.size());
		// Iterator<LeaveBalance> lbs = user.getLeaveBalances().iterator();
		// while (lbs.hasNext()) {
		// lBalance = lbs.next();
		// if (lBalance.getAttType().getId() == apprecord.getAttType().getId())
		// {
		// String attendTypeName = apprecord.getAttType().getName();
		// if (attendTypeName.equals(AttendTypeDAO.otToShift)
		// || attendTypeName.equals(AttendTypeDAO.otToPayment)) {
		// lBalance.setHours(lBalance.getHours() + apprecord.getDuration());
		// } else
		// lBalance.setHours(lBalance.getHours() - apprecord.getDuration());
		// break;
		// }
		// }
		leaveBalanceDao.saveorupdate(lb);
		return false;
	}

	@Override
	public boolean refreshLeavebalance() {
		List<User> users = um.findAllUsers();
		if (users == null)
			return false;
		for (User user : users) {
			List<LeaveBalance> lbs = user.getLeaveBalances();
			if (lbs == null)
				continue;
			;
			for (LeaveBalance lb : lbs) {
				this.refreshLeavebalance(user, lb.getAttType());
			}
		}
		return true;
	}

	@Resource
	public void setVrDao(VacationDAO vrDao) {
		this.vrDao = vrDao;
	}

	@Resource
	public void setLeaveBalanceDao(LeaveBalanceDAO leaveBalanceDao) {
		this.leaveBalanceDao = leaveBalanceDao;
	}

	@Resource
	public void setUm(UserManager um) {
		this.um = um;
	}
}
