package com.tm.ajax;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.tm.service.LeaveBalanceMgr;

@Component
@Lazy
public class RefreshUserLeaveBalanceAjax extends ActionSupport {
	LeaveBalanceMgr lbmgr;
	String tips;
	@Override
	public String execute() throws Exception {
		if (lbmgr.refreshLeavebalance())
			tips = "数据刷新完成";
		else
			tips = "数据刷新失败";
		return SUCCESS;
	}

	@Resource
	public void setLbmgr(LeaveBalanceMgr lbmgr) {
		this.lbmgr = lbmgr;
	}

	public String getTips() {
		return tips;
	}

}
