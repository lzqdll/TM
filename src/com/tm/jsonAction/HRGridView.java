package com.tm.jsonAction;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionContext;
import com.tm.constant.WebConstant;
import com.tm.dao.AppRcdDAO;
import com.tm.entity.User;
import com.tm.service.ApplicationManager;
import com.tm.service.UserManager;
import jqGridBase.JqGridBase;

@Component
@Lazy
public class HRGridView extends JqGridBase {
	private ApplicationManager appManager;
	private UserManager userManager;
	private String searchTable;

	// 部门用户的休假申请记录
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Map session = ActionContext.getContext().getSession();
		User manager = (User) session.get(WebConstant.USER);
		if (manager == null)
			return LOGIN;
		this.assembleSQL();
		if (!manager.getEmployeeType().contains("HR"))
			return ERROR;
		if (searchTable.equals("Vacation")) {
			gridData = appManager.findPagedVacationReords(rows * (page - 1), rows, this.searchSQL);
			setVacationReturnParam(this.searchSQL);
		} else {
			gridData = appManager.findPagedLeaveReords(rows * (page - 1), rows, this.searchSQL);
			setLeaveReturnParam(this.searchSQL);
		}
		System.out.println(gridData.size());
		return super.execute();
	}

	protected void setLeaveReturnParam(String searchSQL) {
		records = appManager.getTotalLeaveRows(searchSQL);
		total = (records - 1) / rows + 1;
	}

	protected void setVacationReturnParam(String searchSQL) {
		records = appManager.getTotalVacationRows(searchSQL);
		total = (records - 1) / rows + 1;
	}

	// 返回某个用户的剩余假期
	public String findUserLeaveBalance() {
		if (this.searchString != null)
			gridData = userManager.findLeaveBalancebyusername(this.getSearchString());
		else
			gridData = null;
		return SUCCESS;
	}

	@Resource
	public void setAppManager(ApplicationManager appManager) {
		this.appManager = appManager;
	}

	public void setSearchTable(String searchTable) {
		this.searchTable = searchTable;
	}

	@Resource
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
