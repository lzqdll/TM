package com.tm.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.apache.struts2.json.annotations.JSON;

@Entity
public class VacationRecord {
	private long id;
	private User user;
	private AttendType attType;
	private float adjustedduration;
	private float initialVacationamount;
	private String status;
	private String description;
	private Date startTime;
	private Date endTime;
	private Date submitTime;
	private Date approvalTime;
	private Date validTime;
	private Date expiredTime;
	private String month;

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	public AttendType getAttType() {
		return attType;
	}

	public void setAttType(AttendType attType) {
		this.attType = attType;
	}

	public float getAdjustedduration() {
		return adjustedduration;
	}

	public void setAdjustedduration(float adjustedduration) {
		this.adjustedduration = adjustedduration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public float getInitialVacationamount() {
		return initialVacationamount;
	}

	public void setInitialVacationamount(float initialVacationamount) {
		this.initialVacationamount = initialVacationamount;
	}
}
