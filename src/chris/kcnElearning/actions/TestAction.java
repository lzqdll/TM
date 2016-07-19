package chris.kcnElearning.actions;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.tm.entity.User;
import com.tm.service.MgrManager;
import com.tm.serviceImpl.ApplicationManagerImpl;
import com.tm.serviceImpl.SystemInitializer;
import com.tm.utils.TMHibernateDaoSupport;

@Component
@Lazy
public class TestAction extends ActionSupport {

	private TMHibernateDaoSupport hibernateDaoSupport;
	private ApplicationManagerImpl appmanager;
	private SystemInitializer sysinitializer;
	private MgrManager managerMgr;

	// manager check application
	public void test5() {
		// managerMgr.checkback(7, "YES");
		User user = new User();
		user = hibernateDaoSupport.getHibernateTemplate().get(User.class, 58l);
		User user1 = hibernateDaoSupport.getHibernateTemplate().load(User.class, 58l);
		List<User> users = hibernateDaoSupport.getHibernateTemplate().find("from User where id='58'");
		System.out.println(users);
	}

	// manager check pending applications
	public void test7() {
		managerMgr.listPendingApplications(3);
	}

	// initialize attendType table
	public void test1() {
		sysinitializer.initializeAttendTypes();
	}

	// initializingUser
	public void test4() {
		User user = new User();
		user.setUsrName("mark");
		user.setUsrPassword("123456");
		user.setDisplayName("mark");
		sysinitializer.addUser(user);
		sysinitializer.setManager((int) user.getId(), 1);
	}

	// Add new application
	public void test2() {
		appmanager.addLeaveApplication(2, 1, new Date(new Date().getTime() - 1000000000), new Date(), 0);
	}

	@Resource(name = "appmanager")
	public void setAppmanager(ApplicationManagerImpl appmanager) {
		// System.out.println("猩猩");
		this.appmanager = appmanager;
	}

	@Resource
	public void setHibernateDaoSupport(TMHibernateDaoSupport hibernateDaoSupport) {
		this.hibernateDaoSupport = hibernateDaoSupport;
	}

	@Resource
	public void setSysinitializer(SystemInitializer sysinitializer) {
		this.sysinitializer = sysinitializer;
	}

	@Resource
	public void setManagerMgr(MgrManager managerMgr) {
		this.managerMgr = managerMgr;
	}
}
