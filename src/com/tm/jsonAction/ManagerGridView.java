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
public class ManagerGridView extends JqGridBase {
	private ApplicationManager appManager;
	private UserManager userManager;
	private String searchTable;
	private String _search;

	// 部门用户的休假申请记录
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Map session = ActionContext.getContext().getSession();
		User manager = (User) session.get(WebConstant.USER);
		if (manager == null)
			return LOGIN;
		this.assembleSQL();
		if (searchTable != null && searchTable.equals("PendingApplication")) {
			this.searchSQL = " AND status='" + AppRcdDAO.inProgress + "'" + this.searchSQL;
			gridData = appManager.findPagedLeaveByManager(manager, rows * (page - 1), rows, this.searchSQL);
			setLeaveReturnParam(manager, this.searchSQL);
		} else if (searchTable != null && searchTable.equals("PendingOTApplication")) {
			this.searchSQL = " AND status='" + AppRcdDAO.inProgress + "'" + this.searchSQL;
			gridData = appManager.findPagedVacationByManager(manager, rows * (page - 1), rows, this.searchSQL);
			setVacationReturnParam(manager, this.searchSQL);
		} else {
			gridData = appManager.findPagedLeaveByManager(manager, rows * (page - 1), rows, this.searchSQL);
		}

		System.out.println(gridData.size());
		return super.execute();
	}

	protected void setLeaveReturnParam(User manager, String seachSQL) {
		records = appManager.getTotalLeaveaRowsByManager(manager, seachSQL);
		total = (records - 1) / rows + 1;
	}

	protected void setVacationReturnParam(User manager, String seachSQL) {
		records = appManager.getTotalVacationRowsByManager(manager, seachSQL);
		total = (records - 1) / rows + 1;
	}

	// 返回某个用户的剩余假期
	public String findUserLeaveBalance() {
		Map session = ActionContext.getContext().getSession();
		User manager = (User) session.get(WebConstant.USER);
		if (this.searchString != null) {
			User usermgr = null;
			User employee = userManager.getUserByName(this.getSearchString());
			if (null != employee)
				usermgr = employee.getManager();
			if (usermgr != null) {
				if (manager.getId() == usermgr.getId())
					gridData = userManager.findLeaveBalancebyusername(this.getSearchString());
			}
		} else
			gridData = null;
		return SUCCESS;
	}

	// protected void setLeaveReturnParam(User manager) {
	// if ("HR".equals(manager.getEmployeeType()))
	// records = appManager.getTotalRows(this.searchSQL);
	// else
	// records = appManager.getTotalRowsByManager(manager, this.searchSQL);
	// total = (records - 1) / rows + 1;
	// }

	public void setSearchTable(String searchTable) {
		this.searchTable = searchTable;
	}

	@Resource
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Resource
	public void setAppManager(ApplicationManager appManager) {
		this.appManager = appManager;
	}

}
