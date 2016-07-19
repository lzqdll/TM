package com.tm.jsonAction;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.tm.constant.WebConstant;
import com.tm.entity.User;
import com.tm.service.ApplicationManager;
import com.tm.service.UserManager;

import jqGridBase.JqGridBase;

@Component
@Lazy
public class UserGridView extends JqGridBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserManager usermanager;
	private ApplicationManager appManager;
	private String searchTable;

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Map session = ActionContext.getContext().getSession();
		User user = (User) session.get(WebConstant.USER);
		if (searchTable.equals("leaveapplication")) {
			this.assembleSQL();
			gridData = appManager.findPagedLeaveByUser(user, rows * (page - 1), rows, searchSQL);
			setLeaveReturnParam(user, this.searchSQL);
		} else if (searchTable.equals("leavebalance")) {
			this.assembleSQL();
			gridData = usermanager.findLeaveBalancebyusername(user.getUsrName());
		} else if (searchTable.equals("OTApplication")) {
			this.assembleSQL();
			gridData = appManager.findPagedVacationByUser(user, rows * (page - 1), rows,
					" AND attType.name in ('Annual_Leave','OTToShift','OTToPayment')" + searchSQL);
			setVacationReturnParam(user, this.searchSQL);
		}
		System.out.println(gridData.size());
		return super.execute();
	}

	protected void setLeaveReturnParam(User user, String seachSQL) {
		records = appManager.getTotalLeaveRowsByUser(user, seachSQL);
		total = (records - 1) / rows + 1;
	}

	protected void setVacationReturnParam(User user, String seachSQL) {
		records = appManager.getTotalVacationRowsByUser(user, seachSQL);
		total = (records - 1) / rows + 1;
	}

	@Resource
	public void setUsermanager(UserManager usermanager) {
		this.usermanager = usermanager;
	}

	public void setSearchTable(String searchTable) {
		this.searchTable = searchTable;
	}

	@Resource
	public void setAppManager(ApplicationManager appManager) {
		this.appManager = appManager;
	}
}
