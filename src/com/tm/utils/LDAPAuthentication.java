package com.tm.utils;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import org.springframework.stereotype.Component;

@Component
public class LDAPAuthentication {
	private final String URL = "ldap://192.168.30.22:389/";
	private final String BASEDN = "ou=Users,OU=GBCNTJ,dc=gorbeltianjin,dc=local";
	private final String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	private LdapContext ctx = null;
	private final Control[] connCtls = null;

	private boolean LDAP_connect() {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY);
		env.put(Context.PROVIDER_URL, URL + BASEDN);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		String root = "cn=administrator,cn=users,dc=gorbeltianjin,dc=local";
		env.put(Context.SECURITY_PRINCIPAL, root);
		env.put(Context.SECURITY_CREDENTIALS, "IT@gorbel.com");
		try {
			ctx = new InitialLdapContext(env, connCtls);
		} catch (javax.naming.AuthenticationException e) {
			System.out.println("LDAP connect 失败");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			System.out.println("LDAP connect 失败");
			e.printStackTrace();
			return false;
		}
		System.out.println("LADP 环境初始化成功");
		return true;
	}

	private String getUserDN(String uid) {
		String userDN = "";
		if (!LDAP_connect())
			return "false";
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> en = ctx.search("", "sAMAccountName=" + uid, constraints);
			// System.out.println(en.hasMore());
			// if (en.hasMoreElements()) {
			// System.out.println("找到到用戶信息了");
			// }
			while (en != null && en.hasMoreElements()) {
				Object obj = en.nextElement();
				if (obj instanceof SearchResult) {
					SearchResult si = (SearchResult) obj;
					userDN += si.getName();
					userDN += "," + BASEDN;
					si.getAttributes().get("sAMAccountName").toString();
					System.out.println(si.getAttributes().get("samaccountname"));
					System.out.println(si.getAttributes().get("userAccountControl").getAttributeDefinition().toString());
				} else {
					System.out.println(obj);
				}
			}
		} catch (Exception e) {
			System.out.println("獲取用戶DN名稱錯誤");
			e.printStackTrace();
		}
		return userDN;
	}

	public boolean authenricate(String UID, String password) {
		boolean result = false;
		if(UID==null || password==null)
			return result;
		String userDN = getUserDN(UID);
		if (userDN.length() > 0 && password.length() > 0) {
			try {
				ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN);
				ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
				ctx.reconnect(connCtls);
				System.out.println("用户DN名称" + userDN);
				result = true;
			} catch (AuthenticationException e) {
				System.out.println(userDN + "验证失败");
				System.out.println(e.toString());
				result = false;
			} catch (NamingException e) {
				System.out.println(userDN + " 验证失败");
				result = false;
			}
		}
		return result;
	}

	public NamingEnumeration<SearchResult> getAllusers() {
		LDAP_connect();
		NamingEnumeration<SearchResult> en = null;
		int i = 0;
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			en = ctx.search("", "objectClass=user", constraints);
			// System.out.println(en.hasMore());
			// if (en == null || !en.hasMoreElements()) {
			// System.out.println("找到用户们的信息了");
			// }
			// maybe more than one element
			// while (en != null && en.hasMoreElements()) {
			// Object obj = en.nextElement();
			// if (obj instanceof SearchResult) {
			// i++;
			// SearchResult si = (SearchResult) obj;
			// // System.out.println(si.getName());
			// System.out.println(si.getAttributes().get("memberof").getAll().next());
			// System.out.println(si.getAttributes().get("sAMAccountName").get(0));
			// System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			// // si.getAttributes().get("sAMAccountName").toString()
			// // System.out.println(si.getAttributes().get("samaccountname"));
			// } else {
			// System.out.println(obj);
			// }
			// }
		} catch (Exception e) {
			System.out.println("用戶列表信息查詢失敗");
			e.printStackTrace();
		}
		System.out.println(i);
		return en;
	}

	public static void main(String[] args) {
		LDAPAuthentication ldap = new LDAPAuthentication();
		// ldap.listAllusers();
		if (ldap.authenricate("zhiliu", "!nnit1709")) {
			System.out.println("用戶验证成功");
		} else
			System.out.println("用户验证失败");

	}
}