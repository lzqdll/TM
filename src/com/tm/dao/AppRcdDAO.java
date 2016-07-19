package com.tm.dao;

import java.util.Date;
import java.util.List;

import com.tm.entity.ApplicationRecord;
import com.tm.entity.User;

public interface AppRcdDAO {

	public ApplicationRecord get(long appid);

	public void saveOrUpdate(ApplicationRecord appRcd);

	public void delete(ApplicationRecord appRcd);

	public int getTotalRowsByManager(User manager);

	public int getTotalRowsByManager(User manager, String searchSQL);

	public int getTotalRowsByUser(User user);

	public int getTotalRowsByUser(User user, String searchSQL);

	List<ApplicationRecord> findPagedApplicationsByUser(User user, int offset, int length);

	List<ApplicationRecord> findPagedApplicationsByUser(User user, int offset, int length, String searchSQL);

	List<ApplicationRecord> findPagedApplicationsByManager(User manager, int offset, int length);

	List<ApplicationRecord> findPagedApplicationsByManager(User manager, int offset, int length, String searchSQL);

	List<ApplicationRecord> findPagedApplications(int offset, int length, String searchSQL);

	List<ApplicationRecord> findApplicationsByUser(User user, String searchSQL);

	public int getTotalRows(String searchSQL);

	public int findbyTimeRange(Date date, User user);

	String inProgress = "待审批-PENDING";

	String APPROVAL = "同意-APPROVED";

	String DENIAL = "拒绝-DECLINED";

}
