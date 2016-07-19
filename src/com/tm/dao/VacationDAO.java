package com.tm.dao;

import java.util.Date;
import java.util.List;

import com.tm.entity.User;
import com.tm.entity.VacationRecord;

public interface VacationDAO {

	VacationRecord get(long id);

	void saveOrUpdate(VacationRecord vr);

	void delete(VacationRecord vacationRecord);

	int getTotalRowsByManager(User manager);

	int getTotalRowsByManager(User manager, String searchSQL);

	int getTotalRowsByUser(User user);

	int getTotalRowsByUser(User user, String searchSQL);

	List<VacationRecord> findPagedRecordByUser(User user, int offset, int length);

	List<VacationRecord> findPagedRecordByUser(User user, int offset, int length, String searchSQL);

	List<VacationRecord> findPagedRecordByManager(User manager, int offset, int length);

	List<VacationRecord> findPagedRecordByManager(User manager, int offset, int length, String searchSQL);

	List<VacationRecord> findPagedRecords(int offset, int length, String searchSQL);

	List<VacationRecord> findRecordsByUser(Object[] obj, String searchSQL);

	int getTotalRows(String searchSQL);

	int findbyTimeRange(Date date, User user);

	String inProgress = "审批中";

	String APPROVAL = "同意";

	String DENIAL = "拒绝";

}
