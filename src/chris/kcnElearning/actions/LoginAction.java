package chris.kcnElearning.actions;

import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tm.constant.WebConstant;
import com.tm.entity.User;
import com.tm.service.UserManager;
import com.tm.utils.LDAPAuthentication;

@Component
@Lazy(true)
@Scope("prototype")
public class LoginAction extends ActionSupport {
	private User quser;
	private UserManager userManager;

	@Override
	public String execute() throws Exception {
		// System.out.println(this.getQuser().getUsrName());
		if (quser == null) {
			return LOGIN;
		}
		// quser.setValid(true);
		quser = userManager.validateUser(quser);
		if (quser != null) {
			// System.out.println("UserID:" + userList.get(0).getId());
			ActionContext.getContext().getSession().put(WebConstant.USER, quser);
			// System.out.println(ActionContext.getContext().getSession().get(WebConstant.USER));
			return super.execute();
		} else
			addActionMessage("用户名或密码不正确！");
		return LOGIN;
	}

	public String logout() {
		ActionContext.getContext().getSession().remove(WebConstant.USER);
		return LOGIN;
	}

	public User getQuser() {
		return quser;
	}

	public void setQuser(User quser) {
		this.quser = quser;
	}

	@Resource
	public void setUsermanager(UserManager userManager) {
		this.userManager = userManager;
	}
}
