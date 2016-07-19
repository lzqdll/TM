package com.tm.ajax;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tm.constant.WebConstant;
import com.tm.entity.User;
import com.tm.service.MgrManager;

@Component
@Lazy
public class CheckApplicationAjax extends ActionSupport {
	private MgrManager mgrManager;
	private long id;
	private boolean result;
	private String type;
	private String tips;

	@Override
	public String execute() throws Exception {
		User user = (User) ActionContext.getContext().getSession().get(WebConstant.USER);
		if (user == null)
			return LOGIN;
		if (mgrManager.checkback(id, result, type))
			tips = "审批成功";
		else
			tips = "审批失败,请检查";
		return SUCCESS;
	}

	@Resource
	public void setMgrManager(MgrManager mgrManager) {
		this.mgrManager = mgrManager;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTips() {
		return tips;
	}
}
