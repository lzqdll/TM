package com.tm.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.tm.dao.AppRcdDAO;
import com.tm.dao.AttendTypeDAO;
import com.tm.dao.UserDAO;
import com.tm.dao.VacationDAO;
import com.tm.entity.ApplicationRecord;
import com.tm.entity.User;
import com.tm.entity.VacationRecord;
import com.tm.service.LeaveBalanceMgr;
import com.tm.service.MgrManager;

@Component
@Lazy
public class MgrManagerImpl implements MgrManager {
	private AppRcdDAO appRcdDao;
	// private AttendTypeDAO attTypeDao;
	// private LeaveBalanceDAO leaveBalanceDao;
	private UserDAO userDao;
	private VacationDAO vrDao;
	private LeaveBalanceMgr lbmgr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tm.serviceImpl.MgrManager#checkback(long, java.lang.String)
	 */
	@Override
	public boolean checkback(long appid, boolean checkresult, String type) {
		if (type.equals("OT")) {
			processOT(appid, checkresult);
		} else {
			ApplicationRecord apprecord = appRcdDao.get(appid);
			if (checkresult) {
				apprecord.setApprovalTime(new Date());
				apprecord.setStatus(AppRcdDAO.APPROVAL);
				String searchSQL = " AND attType.id=" + apprecord.getAttType().getId() + " AND status='"
						+ AppRcdDAO.APPROVAL + "' AND expiredTime>=?" + " order by expiredTime";
				Object[] obj = new Object[] { apprecord.getUser(), apprecord.getEndTime() };
				List<VacationRecord> vrs = vrDao.findRecordsByUser(obj, searchSQL);
				float remainduration = apprecord.getDuration();
				float sumvacation = 0;
				for (int i = 0; i < vrs.size(); i++) {
					sumvacation += vrs.get(i).getAdjustedduration();
					System.out.println(vrs.get(i).getExpiredTime());
				}
				if (sumvacation < remainduration)
					return false;
				VacationRecord vr;
				if (remainduration > 0) {
					for (int i = 0; i < vrs.size(); i++) {
						vr = vrs.get(i);
						if (vr.getAdjustedduration() >= remainduration) {
							vr.setAdjustedduration(vr.getAdjustedduration() - remainduration);
							remainduration = 0;
						} else if (vr.getAdjustedduration() > 0) {
							remainduration = remainduration - vr.getAdjustedduration();
							vr.setAdjustedduration(0);
						}
						vrDao.saveOrUpdate(vr);
					}
				} else {
					vr = new VacationRecord();
					if (vrs.size() > 0)
						vr.setExpiredTime(vrs.get(0).getExpiredTime());
					else {
						Calendar calendar = Calendar.getInstance();
						calendar.roll(Calendar.DAY_OF_YEAR, -1);
						vr.setExpiredTime(calendar.getTime());
					}
					vr.setAdjustedduration(0 - remainduration);
					vr.setInitialVacationamount(-remainduration);
					vr.setAttType(apprecord.getAttType());
					vr.setApprovalTime(new Date());
					vr.setDescription("用户申请撤回");
					vr.setSubmitTime(new Date());
					vr.setStatus(AppRcdDAO.APPROVAL);
					vr.setUser(apprecord.getUser());
					vrDao.saveOrUpdate(vr);
				}
				lbmgr.refreshLeavebalance(apprecord.getUser(), apprecord.getAttType());
				// recaculateLeavebalance(apprecord.getUser(),
				// apprecord.getAttType());
				appRcdDao.saveOrUpdate(apprecord);
			} else {
				apprecord.setApprovalTime(new Date());
				apprecord.setStatus(AppRcdDAO.DENIAL);
				appRcdDao.saveOrUpdate(apprecord);
			}
		}
		return true;
	}

	boolean  processOT(long appid, boolean checkresult) {
		VacationRecord vr = vrDao.get(appid);
		if (!checkresult) {
			vr.setStatus(AppRcdDAO.DENIAL);
			vrDao.saveOrUpdate(vr);
			return true;
		}
		if(vr.getAdjustedduration()<0)
			return false;
		vr.setApprovalTime(new Date());
		Calendar calendar = Calendar.getInstance();
		if (vr.getAttType().getName().equals(AttendTypeDAO.otToShift)) {
			calendar.setTime(vr.getEndTime());
			calendar.add(Calendar.MONTH, 6);
			vr.setExpiredTime(calendar.getTime());
		} else if (vr.getAttType().getName().equals(AttendTypeDAO.otToPayment)) {
			calendar.setTime(vr.getEndTime());
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.MONTH));
			vr.setExpiredTime(calendar.getTime());
		}
		vr.setStatus(AppRcdDAO.APPROVAL);
		vrDao.saveOrUpdate(vr);
		lbmgr.refreshLeavebalance(vr.getUser(), vr.getAttType());
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tm.serviceImpl.MgrManager#listPendingApplications(long)
	 */
	@Override
	public List<ApplicationRecord> listPendingApplications(long manager_id) {
		User manager = userDao.get(manager_id);
		List<User> users = userDao.findUserbyManager(manager, "");
		List<ApplicationRecord> applicationRecords = new ArrayList<ApplicationRecord>();
		for (User user : users) {
			applicationRecords.addAll(appRcdDao.findApplicationsByUser(user, ""));
		}
		return applicationRecords;
	}

	@Override
	public boolean checkback(long appid, String checkresult) {
		// TODO Auto-generated method stub
		return false;
	}

	@Resource
	public void setAppRcdDao(AppRcdDAO appRcdDao) {
		this.appRcdDao = appRcdDao;
	}

	// @Resource
	// public void setAttTypeDao(AttendTypeDAO attTypeDao) {
	// this.attTypeDao = attTypeDao;
	// }
	//
	// @Resource
	// public void setLeaveBalanceDao(LeaveBalanceDAO leaveBalanceDao) {
	// this.leaveBalanceDao = leaveBalanceDao;
	// }

	@Resource
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Resource
	public void setVrDao(VacationDAO vrDao) {
		this.vrDao = vrDao;
	}

	@Resource
	public void setLbmgr(LeaveBalanceMgr lbmgr) {
		this.lbmgr = lbmgr;
	}
}
