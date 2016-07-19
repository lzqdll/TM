package com.tm.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.tm.dao.LeaveBalanceDAO;
import com.tm.entity.LeaveBalance;
import com.tm.entity.User;
import com.tm.utils.TMHibernateDaoSupport;

@Component
@Lazy(true)
public class LeaveBalanceDAOImpl implements LeaveBalanceDAO {
	TMHibernateDaoSupport tmhibernateTemplate;

	@Override
	public LeaveBalance get(int id) {
		// TODO Auto-generated method stub
		return this.tmhibernateTemplate.getHibernateTemplate().get(LeaveBalance.class, id);
	}

	@Override
	public void saveorupdate(LeaveBalance leaveBalance) {
		// TODO Auto-generated method stub
		this.tmhibernateTemplate.getHibernateTemplate().flush();
		this.tmhibernateTemplate.getHibernateTemplate().saveOrUpdate(leaveBalance);
	}

	@Override
	public void delete(LeaveBalance leaveBalance) {
		// TODO Auto-generated method stub
		this.tmhibernateTemplate.getHibernateTemplate().delete(leaveBalance);
	}

	@Override
	public List<LeaveBalance> findALL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LeaveBalance> findByUser(User user) {
		// TODO Auto-generated method stub
		return (List<LeaveBalance>) this.tmhibernateTemplate.getHibernateTemplate().find("from LeaveBalance");
	}

	@Autowired
	public void setTmhibernateTemplate(TMHibernateDaoSupport tmhibernateTemplate) {
		this.tmhibernateTemplate = tmhibernateTemplate;
	}

	@Override
	public List getLeaveBalancebyCriteria(DetachedCriteria criteria) {
		// TODO Auto-generated method stub
		return this.tmhibernateTemplate.getHibernateTemplate().findByCriteria(criteria);
	}
}
