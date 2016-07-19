package com.tm.ajax;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;
import com.tm.entity.AttendType;
import com.tm.entity.User;
import com.tm.service.MgrManager;
import com.tm.serviceImpl.SystemInitializer;

@Component
@Lazy
public class VacationAdjustmentAjax extends ActionSupport {
	private User user;
	private AttendType attType;
	private Date validTime;
	private Date expiredTime;
	private float adjustedduration;
	private String description;
	private SystemInitializer sysini;
	private String tips;

	@Override
	public String execute() throws Exception {
		if (sysini.vacationadjustment(user, attType, adjustedduration, validTime, expiredTime, description))
			tips = "添加记录成功";
		else
			tips = "记录添加失败";
		return SUCCESS;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAttType(AttendType attType) {
		this.attType = attType;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public void setAdjustedduration(float adjustedduration) {
		this.adjustedduration = adjustedduration;
	}

	@Resource
	public void setSysini(SystemInitializer sysini) {
		this.sysini = sysini;
	}

	public String getTips() {
		return tips;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
