package com.tm.dao;

import java.util.List;

import com.tm.entity.AttendType;

public interface AttendTypeDAO {
	AttendType get(int id);

	int saveorupdate(AttendType attType);

	void delete(AttendType attType);

	List<AttendType> getAttendTypes();

	String otToShift = "OTToShift";
	String otToPayment = "OTToPayment";
	String annualLeave = "Annual_Leave";
}
