package com.tm.ajax;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tm.constant.WebConstant;
import com.tm.entity.AttendType;
import com.tm.entity.User;
import com.tm.service.ApplicationManager;
import com.tm.utils.MailNotifier;

@Component
@Lazy
public class EditOTApplicationRecordAjax extends ActionSupport {
	private ApplicationManager appManager;
	private String oper;
	private AttendType attType;
	private Date endTime;
	private Date startTime;
	private float adjustedduration;
	private String description;
	private String tips;
	private MailNotifier maillnotifier;
	private TaskExecutor taskExecutor;

	@Override
	public String execute() throws Exception {
		User user = (User) ActionContext.getContext().getSession().get(WebConstant.USER);
		if (user == null)
			return LOGIN;
		if (endTime.getTime() < startTime.getTime()) {
			tips = "结束时间不能早于起始时间";
			return super.execute();
		}
		if (adjustedduration < 0) {
			tips = "申请加班不能为负数";
			return super.execute();
		}
		if (oper.equals("add")) {
			if (appManager.addOTApplication(user.getId(), attType.getId(), startTime, endTime, adjustedduration,
					description)) {
				tips = "申请成功";
				String msg = "Dear Manager \n" + user.getDisplayName()
						+ "\n提交一条加班申请,正等待您的审批.  请尽快登录休假管理系统完成该审批 \nhttp://xjjb/TM \n A new OT request";
				String subject = "New OT Request";
				String[] mailto = user.getManager().getEmail().split(";");
				maillnotifier.setContent(msg);
				maillnotifier.setSubject(subject);
				maillnotifier.setMailto(mailto);
				taskExecutor.execute(maillnotifier);
			} else
				tips = "申请未成功,请检查时间是否有冲突";
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

	public void setAdjustedduration(float adjustedduration) {
		this.adjustedduration = adjustedduration;
	}

	public void setDescription(String description) {
		this.description = description;
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
