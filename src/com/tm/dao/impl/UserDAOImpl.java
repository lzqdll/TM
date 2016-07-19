package com.tm.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.tm.dao.UserDAO;
import com.tm.entity.User;
import com.tm.utils.TMHibernateDaoSupport;

@Component
@Lazy(true)
public class UserDAOImpl implements UserDAO {
	TMHibernateDaoSupport tmhibernateTemplate;

	@Override
	public User get(long id) {
		// TODO Auto-generated method stub
		return this.tmhibernateTemplate.getHibernateTemplate().get(User.class, id);
	}

	@Override
	public User getByName(String name) {
		// TODO Auto-generated method stub
		List<User> emps = (List<User>) this.tmhibernateTemplate.getHibernateTemplate()
				.find("from User where usrName = ? ", name);
		if (emps != null && emps.size() >= 1) {
			return emps.get(0);
		} else
			return null;
	}

	@Override
	public void saveorupdate(User user) {
		// TODO Auto-generated method stub
		this.tmhibernateTemplate.getHibernateTemplate().flush();
		this.tmhibernateTemplate.getHibernateTemplate().saveOrUpdate(user);
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		this.tmhibernateTemplate.getHibernateTemplate().update(user);
	}

	@Override
	public List<User> findALL() {
		// TODO Auto-generated method stub
		return (List<User>) this.tmhibernateTemplate.getHibernateTemplate().find("from User");
	}

	@Autowired
	public void setTmhibernateTemplate(TMHibernateDaoSupport tmhibernateTemplate) {
		this.tmhibernateTemplate = tmhibernateTemplate;
	}

	@Override
	public List<User> findbyUsernameAndPassword(User user) {
		// TODO Auto-generated method stub
		return tmhibernateTemplate.getHibernateTemplate().find("from User as u where u.usrName=? and u.usrPassword=?",
				user.getUsrName(), user.getUsrPassword());
	}

	@Override
	public int getTotalRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> findUserbyManager(User manager, String searchSQL) {
		// TODO Auto-generated method stub
		String hql = "From User as u where u.manager=?" + searchSQL;
		return tmhibernateTemplate.getHibernateTemplate().find(hql, manager);
	}

}
