package com.tm.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.tm.dao.AppRcdDAO;
import com.tm.dao.AttendTypeDAO;
import com.tm.dao.LeaveBalanceDAO;
import com.tm.dao.UserDAO;
import com.tm.dao.VacationDAO;
import com.tm.entity.AttendType;
import com.tm.entity.LeaveBalance;
import com.tm.entity.User;
import com.tm.entity.VacationRecord;
import com.tm.service.LeaveBalanceMgr;
import com.tm.service.MgrManager;

@Component
@Lazy
public class SystemInitializer {
	private AppRcdDAO appRcdDao;
	private AttendTypeDAO attTypeDao;
	private LeaveBalanceDAO leaveBalanceDao;
	private VacationDAO vacationDao;
	private UserDAO userDao;
	private MgrManager mgrManager;
	private LeaveBalanceMgr lbmgr;

	public void setManager(int userid, int mangerID) {
		User user = userDao.get(userid);
		User manager = userDao.get(mangerID);
		user.setManager(manager);
		userDao.saveorupdate(user);
	}
	public void addUser(User user) {
		initializeUserleaveBalance(user);
	}

	public boolean initializeUserleaveBalance(User user) {
		List<AttendType> attendTypes;
		attendTypes = attTypeDao.getAttendTypes();
		Iterator<AttendType> it = attendTypes.iterator();
		while (it.hasNext()) {
			LeaveBalance lb = new LeaveBalance();
			lb.setUser(user);
			lb.setAttType(it.next());
			DetachedCriteria criteria = DetachedCriteria.forClass(LeaveBalance.class).add(Restrictions.eq("user", user))
					.add(Restrictions.eq("attType", lb.getAttType()));
			@SuppressWarnings("unchecked")
			List<LeaveBalance> lbs = leaveBalanceDao.getLeaveBalancebyCriteria(criteria);
			if (lbs.size() < 1) {
				lb.setHours(80);
				leaveBalanceDao.saveorupdate(lb);
			}
		}
		return true;
	}

	public boolean vacationadjustment(User user, AttendType attendType, float adjustedduration, Date startingValidTime,
			Date endingValidTime, String description) {
		user = userDao.getByName(user.getUsrName());
		if (user == null) {
		} else {
			VacationRecord vr = new VacationRecord();
			vr.setUser(user);
			vr.setAttType(attendType);
			vr.setSubmitTime(new Date());
			vr.setValidTime(startingValidTime);
			if (description == null)
				vr.setDescription("HR 调整");
			else
				vr.setDescription(description);
			if (endingValidTime == null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(startingValidTime);
				calendar.set(calendar.get(Calendar.YEAR) + 1, 2, 31);
				endingValidTime = calendar.getTime();
			}
			vr.setExpiredTime(endingValidTime);
			if (adjustedduration == 0) {
				if (user.getStartWorkingTime() == null)
					return false;
				else {
					Calendar c1 = Calendar.getInstance();
					Calendar c2 = Calendar.getInstance();
					c1.setTime(startingValidTime);
					c2.setTime(user.getStartWorkingTime());
					float workinglenth = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
					float monthl = (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH)) / 12;
					workinglenth = workinglenth + monthl;
					if (workinglenth < 1)
						adjustedduration = 0;
					else if (workinglenth >= 1 && workinglenth < 9.5)
						adjustedduration = 5;
					else if (workinglenth >= 9.5 && workinglenth < 19.5)
						adjustedduration = 10;
					else
						adjustedduration = 10;
				}
			}
			vr.setAdjustedduration(adjustedduration);
			vr.setInitialVacationamount(adjustedduration);
			vr.setStatus(appRcdDao.APPROVAL);
			vacationDao.saveOrUpdate(vr);
			lbmgr.refreshLeavebalance(user, attendType);
//			mgrManager.recaculateLeavebalance(user, attendType);
			return true;
		}
		return false;
	}

	public void initializeAttendTypes() {
		AttendType attType = new AttendType();
		attType.setName("Annual_Leave");
		attTypeDao.saveorupdate(attType);
		attType = new AttendType();
		attType.setName("Paid_Sick_Leave");
		attTypeDao.saveorupdate(attType);
		attType = new AttendType();
		attType.setName(AttendTypeDAO.otToShift);
		attTypeDao.saveorupdate(attType);
	}

	@Resource
	public void setAppRcdDao(AppRcdDAO appRcdDao) {
		this.appRcdDao = appRcdDao;
	}

	@Resource
	public void setAttTypeDao(AttendTypeDAO attTypeDao) {
		this.attTypeDao = attTypeDao;
	}

	@Resource
	public void setLeaveBalanceDao(LeaveBalanceDAO leaveBalanceDao) {
		this.leaveBalanceDao = leaveBalanceDao;
	}

	@Resource
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Resource
	public void setVacationDao(VacationDAO vacationDao) {
		this.vacationDao = vacationDao;
	}

	@Resource
	public void setMgrManager(MgrManager mgrManager) {
		this.mgrManager = mgrManager;
	}
	@Resource
	public void setLbmgr(LeaveBalanceMgr lbmgr) {
		this.lbmgr = lbmgr;
	}
}
