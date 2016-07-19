package com.tm.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Component;
@Component
public class MailNotifier implements Runnable {
	private String content;
	private String subject;
	private String[] mailto;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
		this.simpletxtemail(content, subject, mailto);
		System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
	}

	public void simpletxtemail(String content, String subject, String[] mailto) {
		Email email = new SimpleEmail();
		email.setHostName("192.168.30.27");
		email.setSmtpPort(25);
		email.setAuthenticator(new DefaultAuthenticator("sysalert", "P@ssw0rd"));
		email.setSSLOnConnect(false);
		// email.setSslSmtpPort(443);
		try {
			email.setFrom("SysAlert@gorbel.com.cn");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		email.setSubject(subject);
		try {
			email.setMsg(content);
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			for (String mail : mailto)
				email.addTo(mail);
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			email.send();
			System.out.println("Mail Sent to " + mailto.toString());
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public MailNotifier() {
	}

	public MailNotifier(String content, String subject, String[] mailto) {
		super();
		this.content = content;
		this.subject = subject;
		this.mailto = mailto;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setMailto(String[] mailto) {
		this.mailto = mailto;
	}
}
