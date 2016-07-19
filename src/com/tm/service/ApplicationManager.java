package com.tm.service;

import java.util.Date;
import java.util.List;

import com.tm.entity.ApplicationRecord;
import com.tm.entity.User;
import com.tm.entity.VacationRecord;

public interface ApplicationManager {

	ApplicationRecord addLeaveApplication(long user_id, int attType_id, Date startTime, Date endTime, float duration);

	ApplicationRecord addApplication(ApplicationRecord appRecord);

	int getTotalLeaveRowsByUser(User user, String searchSQL);

	int getTotalLeaveaRowsByManager(User manager, String searchSQL);

	int getTotalLeaveRows(String searchSQL);

	int getTotalVacationRowsByUser(User user, String searchSQL);

	int getTotalVacationRowsByManager(User manager, String searchSQL);

	int getTotalVacationRows(String searchSQL);

	List<ApplicationRecord> findPagedLeaveByUser(User user, int offset, int length, String searchSQL);

	List<ApplicationRecord> findPagedLeaveByManager(User manager, int offset, int length, String searchSQL);

	List<ApplicationRecord> findPagedLeaveReords(int offset, int length, String searchSQL);

	List<VacationRecord> findPagedVacationByManager(User manager, int offset, int length, String searchSQL);

	List<VacationRecord> findPagedVacationByUser(User user, int offset, int length, String searchSQL);

	List<VacationRecord> findPagedVacationReords(int offset, int length, String searchSQL);

	boolean addOTApplication(long user_id, int attType_id, Date startTime, Date endTime, float duration,
			String description);
	boolean delete(long id);
}