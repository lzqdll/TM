package com.tm.serviceImpl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.tm.constant.WebConstant;
import com.tm.dao.AppRcdDAO;
import com.tm.dao.AttendTypeDAO;
import com.tm.dao.LeaveBalanceDAO;
import com.tm.dao.UserDAO;
import com.tm.dao.VacationDAO;
import com.tm.entity.ApplicationRecord;
import com.tm.entity.AttendType;
import com.tm.entity.LeaveBalance;
import com.tm.entity.User;
import com.tm.entity.VacationRecord;
import com.tm.service.ApplicationManager;

@Component("appmanager")
@Lazy
public class ApplicationManagerImpl implements ApplicationManager {
	private AppRcdDAO appRcdDao;
	private AttendTypeDAO attTypeDao;
	private LeaveBalanceDAO leaveBalanceDao;
	private VacationDAO vrDAO;
	private UserDAO userDao;

	@Override
	public ApplicationRecord addLeaveApplication(long user_id, int attType_id, Date startTime, Date endTime,
			float duration) {
		AttendType attType = attTypeDao.get(attType_id);
		User user = userDao.get(user_id);
		ApplicationRecord appRecord = new ApplicationRecord();
		appRecord.setAttType(attType);
		appRecord.setUser(user);
		appRecord.setSubmitTime(new Date());
		appRecord.setStartTime(startTime);
		appRecord.setEndTime(endTime);
		// float leaveHours = (endTime.getTime() - startTime.getTime()) /
		// 1000;// 除1000计算出秒
		appRecord.setDuration(duration);
		appRecord.setStatus(AppRcdDAO.inProgress);
		if (validatingApplicationProcess(appRecord, user)) {
			appRcdDao.saveOrUpdate(appRecord);
			notifyApprover("OT 审核通过");
			return appRecord;
		} else {
			notifyApprover("OT 审核未通过");
			return null;
		}
	}

	@Override
	public boolean addOTApplication(long user_id, int attType_id, Date startTime, Date endTime, float duration,
			String description) {
		AttendType attType = attTypeDao.get(attType_id);
		User user = userDao.get(user_id);
		if (attType.getName().equals(attTypeDao.otToShift) || attType.getName().equals(attTypeDao.otToPayment)) {
			VacationRecord vr = new VacationRecord();
			vr.setInitialVacationamount(duration);
			vr.setAdjustedduration(duration);
			vr.setStartTime(startTime);
			vr.setAttType(attType);
			vr.setEndTime(endTime);
			vr.setSubmitTime(new Date());
			vr.setUser(user);
			vr.setStatus(AppRcdDAO.inProgress);
			vr.setDescription(description);
			vrDAO.saveOrUpdate(vr);
			return true;
		} else
			return false;
	}

	@Override
	public ApplicationRecord addApplication(ApplicationRecord appRecord) {
		// TODO Auto-generated method stub
		appRecord.setStatus(AppRcdDAO.inProgress);
		appRecord.setSubmitTime(new Date());
		appRecord.setDuration((appRecord.getEndTime().getTime() - appRecord.getStartTime().getTime()) / 1000 / 3600);
		if (validatingApplicationProcess(appRecord, null)) {
			appRcdDao.saveOrUpdate(appRecord);
			notifyApprover("OT 审核通过");
			return appRecord;
		} else {
			notifyApprover("OT 审核未通过");
			return null;
		}
	}

	private void notifyApprover(String s) {
		// TODO Auto-generated method stub
		if (s != null)
			System.out.println(s);
		else
			System.out.println(WebConstant.tips);
	}

	private boolean validatingApplicationProcess(ApplicationRecord appRecord, User user) {
		boolean result = true;
		String attentTypeName = appRecord.getAttType().getName();
		if (!(AttendTypeDAO.otToShift.equals(attentTypeName) || AttendTypeDAO.otToPayment.equals(attentTypeName))) {
			Iterator<LeaveBalance> it = appRecord.getUser().getLeaveBalances().iterator();
			LeaveBalance leaveBalance = new LeaveBalance();
			while (it.hasNext()) {
				leaveBalance = it.next();
				if (leaveBalance.getAttType().getId() == appRecord.getAttType().getId()) {
					if (leaveBalance.getHours() >= appRecord.getDuration()) {
						result = true;
						break;
					} else {
						result = false;
						return result;
					}
				}
			}
		}
		if (appRcdDao.findbyTimeRange(appRecord.getStartTime(), user) > 0
				|| appRcdDao.findbyTimeRange(appRecord.getEndTime(), user) > 0)
			result = false;
		return result;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		if(id<0)
			return false;
		ApplicationRecord ap=appRcdDao.get(id);
		appRcdDao.delete(ap);
		return true;
	}
	@Override
	public int getTotalLeaveRowsByUser(User user, String searchSQL) {
		return appRcdDao.getTotalRowsByUser(user, searchSQL);
	}

	@Override
	public int getTotalLeaveaRowsByManager(User manager, String searchSQL) {
		// TODO Auto-generated method stub
		return appRcdDao.getTotalRowsByManager(manager, searchSQL);
	}

	@Override
	public int getTotalLeaveRows(String searchSQL) {
		// TODO Auto-generated method stub
		return appRcdDao.getTotalRows(searchSQL);
	}

	@Override
	public int getTotalVacationRowsByUser(User user, String searchSQL) {
		// TODO Auto-generated method stub
		return vrDAO.getTotalRowsByUser(user, searchSQL);
	}

	@Override
	public int getTotalVacationRowsByManager(User manager, String searchSQL) {
		// TODO Auto-generated method stub
		return vrDAO.getTotalRowsByManager(manager, searchSQL);
	}

	@Override
	public int getTotalVacationRows(String searchSQL) {
		// TODO Auto-generated method stub
		return vrDAO.getTotalRows(searchSQL);
	}

	@Override
	public List<ApplicationRecord> findPagedLeaveByUser(User user, int offset, int length, String searchSQL) {
		// TODO Auto-generated method stub
		return appRcdDao.findPagedApplicationsByUser(user, offset, length, searchSQL);
	}

	@Override
	public List<ApplicationRecord> findPagedLeaveByManager(User manager, int offset, int length, String searchSQL) {
		return appRcdDao.findPagedApplicationsByManager(manager, offset, length, searchSQL);
	}

	@Override
	public List<ApplicationRecord> findPagedLeaveReords(int offset, int length, String searchSQL) {
		// TODO Auto-generated method stub
		return appRcdDao.findPagedApplications(offset, length, searchSQL);
	}

	@Override
	public List<VacationRecord> findPagedVacationByUser(User user, int offset, int length, String searchSQL) {
		return vrDAO.findPagedRecordByUser(user, offset, length, searchSQL);
	}

	@Override
	public List<VacationRecord> findPagedVacationByManager(User manager, int offset, int length, String searchSQL) {
		// TODO Auto-generated method stub
		return vrDAO.findPagedRecordByManager(manager, offset, length, searchSQL);
	}

	@Override
	public List<VacationRecord> findPagedVacationReords(int offset, int length, String searchSQL) {
		// TODO Auto-generated method stub
		return vrDAO.findPagedRecords(offset, length, searchSQL);
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
	public void setVrDAO(VacationDAO vrDAO) {
		this.vrDAO = vrDAO;
	}
}
