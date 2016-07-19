package com.tm.ajax;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tm.constant.WebConstant;
import com.tm.entity.ApplicationRecord;
import com.tm.entity.AttendType;
import com.tm.entity.User;
import com.tm.service.ApplicationManager;
import com.tm.utils.MailNotifier;

@Component
@Lazy
public class EditLeaveApplicationRecordAjax extends ActionSupport {
	private ApplicationManager appManager;
	private String oper;
	private AttendType attType;
	private String id;
	private Date endTime;
	private Date startTime;
	private float duration;
	private String tips;
	private TaskExecutor taskExecutor;
	private MailNotifier maillnotifier;

	@Override
	public String execute() throws Exception {
		User user = (User) ActionContext.getContext().getSession().get(WebConstant.USER);
		if (user == null)
			return LOGIN;
		if (oper.equals("add")) {
			if (endTime.getTime() < startTime.getTime()) {
				tips = "结束时间不能早于起始时间";
				return super.execute();
			}
			ApplicationRecord ar = appManager.addLeaveApplication(user.getId(), attType.getId(), startTime, endTime,
					duration);
			if (ar != null) {
				tips = "申请成功";
				String msg = "Dear Manager \n" + user.getDisplayName()
						+ "\n 提交一条休假申请，正等待您的审批. \n请尽快登录休假管理系统完成该审批 \n http://xjjb/TM \n A new Time off request";
				String subject = "New Time Off Request";
				String[] mailto = user.getManager().getEmail().split(";");
				MailNotifier maillnotifier = new MailNotifier(msg, subject, mailto);
				maillnotifier.setContent(msg);
				maillnotifier.setSubject(subject);
				maillnotifier.setMailto(mailto);
				taskExecutor.execute(maillnotifier);
				// maillnotifier.simpletxtemail(msg, subject, mailto);
			} else
				tips = "申请未成功,请检查时间是否有冲突";
		} else if (oper.equals("del")) {
			long lid = Long.parseLong(id);
			appManager.delete(lid);
		}
		return super.execute();
	}

	@Resource
	public void setAppManager(ApplicationManager appManager) {
		this.appManager = appManager;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public AttendType getAttType() {
		return attType;
	}

	public void setAttType(AttendType attType) {
		this.attType = attType;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getTips() {
		return tips;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Resource
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	@Resource
	public void setMaillnotifier(MailNotifier maillnotifier) {
		this.maillnotifier = maillnotifier;
	}

}
