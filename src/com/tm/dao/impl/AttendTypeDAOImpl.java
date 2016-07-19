package com.tm.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.tm.dao.AttendTypeDAO;
import com.tm.entity.AttendType;
import com.tm.utils.TMHibernateDaoSupport;

@Component
@Lazy(true)
public class AttendTypeDAOImpl implements AttendTypeDAO {
	TMHibernateDaoSupport tmhibernateTemplate;

	@Override
	public AttendType get(int id) {
		// TODO Auto-generated method stub
		return this.tmhibernateTemplate.getHibernateTemplate().get(AttendType.class, id);
	}

	@Override
	public int saveorupdate(AttendType attType) {
		// TODO Auto-generated method stub
		return (Integer) this.tmhibernateTemplate.getHibernateTemplate().save(attType);
	}

	@Override
	public void delete(AttendType attType) {
		// TODO Auto-generated method stub
		this.tmhibernateTemplate.getHibernateTemplate().delete(attType);
	}

	@Override
	public List<AttendType> getAttendTypes() {
		// TODO Auto-generated method stub
		return (List<AttendType>) this.tmhibernateTemplate.getHibernateTemplate().find("from AttendType");
	}

	@Autowired
	public void setTmhibernateTemplate(TMHibernateDaoSupport tmhibernateTemplate) {
		this.tmhibernateTemplate = tmhibernateTemplate;
	}

}
