package chris.kcnElearning.actions;

import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tm.constant.WebConstant;
import com.tm.entity.User;


public class UserVerifyAction extends ActionSupport {
	// private ExamManger examManger;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Map session = ActionContext.getContext().getSession();
		User user = (User) session.get(WebConstant.USER);
		if (user != null)
			return super.execute();
		else
			return LOGIN;
	}
}
