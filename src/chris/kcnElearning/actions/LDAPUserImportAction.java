package chris.kcnElearning.actions;

import java.util.Iterator;

import javax.annotation.Resource;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchResult;

import com.opensymphony.xwork2.ActionSupport;
import com.tm.entity.User;
import com.tm.service.UserManager;
import com.tm.serviceImpl.SystemInitializer;
import com.tm.utils.LDAPAuthentication;

public class LDAPUserImportAction extends ActionSupport {
	private LDAPAuthentication ldapAuthor;
	private UserManager userManager;
	private SystemInitializer sysInitor;

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		importuser();
		updateUserRelation();
		return ERROR;
	}

	private void importuser() {
		NamingEnumeration<SearchResult> en = ldapAuthor.getAllusers();
		while (en != null && en.hasMoreElements()) {
			Object obj = en.nextElement();
			if (obj instanceof SearchResult) {
				SearchResult si = (SearchResult) obj;
				// System.out.println(si.getName());
				try {
					User user = userManager.getUserByName(si.getAttributes().get("sAMAccountName").get(0).toString());
					if (user == null)
						user = new User();
					if (si.getAttributes().get("sAMAccountName").get(0) != null) {
						System.out.println(si.getAttributes().get("sAMAccountName").get(0).toString());
						user.setUsrName(si.getAttributes().get("sAMAccountName").get(0).toString());
					}
					if (si.getAttributes().get("displayName").get(0) != null) {
//						System.out.println(si.getAttributes().get("displayName").get(0).toString());
						user.setDisplayName(si.getAttributes().get("displayName").get(0).toString());
					}
					if (si.getAttributes().get("distinguishedName").get(0) != null) {
//						System.out.println(si.getAttributes().get("distinguishedName").get(0).toString());
						user.setDistinguishedName(si.getAttributes().get("distinguishedName").get(0).toString());
					}
					if (null!=si.getAttributes().get("mail").get(0)) {
//						System.out.println(si.getAttributes().get("mail").get(0).toString());
						user.setEmail(si.getAttributes().get("mail").get(0).toString());
					}
					if (si.getAttributes().get("employeeType") != null) {
//						System.out.println(si.getAttributes().get("employeeType").get(0).toString());
						user.setEmployeeType(si.getAttributes().get("employeeType").get(0).toString());
					}
					if (si.getAttributes().get("manager") != null) {
//						System.out.println(si.getAttributes().get("manager").get(0).toString());
						user.setManagerDistinguishedName(si.getAttributes().get("manager").get(0).toString());
					}
					userManager.addorUpdateUser(user);
					// 初始化用户剩余假期
					// sysInitor.initializeUserleaveBalance(user);
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					// si.getAttributes().get("sAMAccountName").toString()
					// System.out.println(si.getAttributes().get("samaccountname"));
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				System.out.println(obj);
			}
		}

	}

	private void updateUserRelation() throws Exception {
		User user = null;
		User manager = null;
		Iterator<User> users = userManager.findAllUsers().iterator();
		while (users != null && users.hasNext()) {
			user = users.next();
			if (user.getManagerDistinguishedName() == null)
				continue;
			Iterator<User> managers = userManager.findAllUsers().iterator();
			while (managers.hasNext()) {
				manager = managers.next();
				if (user.getManagerDistinguishedName().equals(manager.getDistinguishedName())) {
					user.setManager(manager);
					break;
				}
			}
			userManager.addorUpdateUser(user);
		}
	}

	@Resource
	public void setLdapAuthor(LDAPAuthentication ldapAuthor) {
		this.ldapAuthor = ldapAuthor;
	}

	@Resource
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Resource
	public void setSysInitor(SystemInitializer sysInitor) {
		this.sysInitor = sysInitor;
	}
}
