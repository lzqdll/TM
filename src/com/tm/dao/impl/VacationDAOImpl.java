package com.tm.dao.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import com.tm.dao.VacationDAO;
import com.tm.entity.VacationRecord;
import com.tm.entity.User;
import com.tm.entity.VacationRecord;
import com.tm.utils.TMHibernateDaoSupport;

@Component
@Lazy
public class VacationDAOImpl implements VacationDAO {
	TMHibernateDaoSupport tmhibernateTemplate;

	@Override
	public VacationRecord get(long id) {
		// TODO Auto-generated method stub
		return tmhibernateTemplate.getHibernateTemplate().get(VacationRecord.class, id);
	}

	@Resource
	public void setTmhibernateTemplate(TMHibernateDaoSupport tmhibernateTemplate) {
		this.tmhibernateTemplate = tmhibernateTemplate;
	}

	@Override
	public void saveOrUpdate(VacationRecord vacationRecord) {
		// TODO Auto-generated method stub
		tmhibernateTemplate.getHibernateTemplate().flush();
		tmhibernateTemplate.getHibernateTemplate().saveOrUpdate(vacationRecord);
	}

	@Override
	public void delete(VacationRecord vacationRecord) {
		// TODO Auto-generated method stub
		tmhibernateTemplate.getHibernateTemplate().delete(vacationRecord);
	}

	@Override
	public List<VacationRecord> findPagedRecordByUser(User user, int offset, int length, String searchSQL) {
		// TODO Auto-generated method stub
		String hql = "from VacationRecord as a where a.user=?" + searchSQL;
		System.out.println(hql);
		List list = tmhibernateTemplate.findByPage(hql, user, offset, length);
		return list;
	}

	@Override
	public List<VacationRecord> findPagedRecordByManager(User manager, int offset, int length,
			String searchSQL) {
		// TODO Auto-generated method stub
		String hql = "from VacationRecord as a where  a.user.manager=? " + searchSQL;
		System.out.println(hql);
		List list = tmhibernateTemplate.findByPage(hql, manager, offset, length);
		return list;
	}

	@Override
	public int getTotalRowsByManager(User manager) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from VacationRecord as a  where a.user.manager=?";
		int total = 0;
		try {
			Object obj = tmhibernateTemplate.getHibernateTemplate().find(hql, manager).listIterator().next();
			total = Integer.parseInt(obj.toString());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return total;
	}

	@Override
	public int getTotalRowsByManager(User manager, String searchSQL) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from VacationRecord as a  where  a.user.manager=? " + searchSQL;
		int total = 0;
		try {
			Object obj = tmhibernateTemplate.getHibernateTemplate().find(hql, manager).listIterator().next();
			total = Integer.parseInt(obj.toString());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return total;
	}

	@Override
	public int getTotalRowsByUser(User user) {
		String hql = "select count(*) from VacationRecord as a where a.user=?";
		int total = 0;
		try {
			Object i = tmhibernateTemplate.getHibernateTemplate().find(hql, user).listIterator().next();
			total = Integer.parseInt(i.toString());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return total;
	}

	@Override
	public int getTotalRowsByUser(User user, String searchSQL) {
		String hql = "select count(*) from VacationRecord as a  where a.user=? " + searchSQL;
		int total = Integer
				.parseInt(tmhibernateTemplate.getHibernateTemplate().find(hql, user).listIterator().next().toString());
		return total;
	}

	@Override
	public List<VacationRecord> findPagedRecordByUser(User user, int offset, int length) {
		String hql = "from VacationRecord as a where a.user=?";
		System.out.println(hql);
		List list = tmhibernateTemplate.findByPage(hql, user, offset, length);
		return list;
	}

	@Override
	public List<VacationRecord> findPagedRecordByManager(User manager, int offset, int length) {
		String hql = "from VacationRecord as a where a.user.manager=?";
		List list = tmhibernateTemplate.findByPage(hql, manager, offset, length);
		return list;
	}

	public int findbyTimeRange(Date date, User user) {
		int i = 0;
		String hql = "from VacationRecord where user=? and startTime<=? and endTime>=?";
		List list = tmhibernateTemplate.getHibernateTemplate().find(hql, new Object[] { user, date, date });
		i = list.size();
		return i;

	}

	@Override
	public List<VacationRecord> findPagedRecords(int offset, int length, String searchSQL) {
		String hql = "from VacationRecord as a where 1=1 " + searchSQL;
		return tmhibernateTemplate.findByPage(hql, offset, length);
	}

	@Override
	public int getTotalRows(String searchSQL) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from VacationRecord as a where 1=1 " + searchSQL;
		int total = 0;
		try {
			Object i = tmhibernateTemplate.getHibernateTemplate().find(hql).listIterator().next();
			total = Integer.parseInt(i.toString());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return total;
	}

	@Override
	public List<VacationRecord> findRecordsByUser(Object[] obj, String searchSQL) {
		// TODO Auto-generated method stub
		String hql = "from VacationRecord as a where a.user=?" + searchSQL;
		System.out.println(hql);
		List list = tmhibernateTemplate.getHibernateTemplate().find(hql, obj);
		return list;
	}
}
