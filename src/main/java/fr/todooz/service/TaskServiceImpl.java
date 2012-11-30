package fr.todooz.service;

import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.Interval;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.todooz.domain.Task;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Inject
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void save(Task task) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(task);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from Task where id=:id");
		query.setLong("id", id);
		query.executeUpdate();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Task order by date desc");
		List<Task> tasks = query.list();
		return tasks;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByQuery(String query) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Task.class);
		criteria.add(Restrictions.or(Restrictions.like("title", "%" + query + "%"), Restrictions.like("tags", "%" + query + "%")));
		List<Task> tasks = criteria.list();
		return tasks;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Task> findByTag(String query) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Task.class);
		criteria.add(Restrictions.like("tags", "%" + query + "%"));
		List<Task> tasks = criteria.list();
		return tasks;
	}

	@Override
	@Transactional(readOnly = true)
	public int count() {
		return findAll().size();
	}
	
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByInterval(Interval interval) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Task.class);
		criteria.add(Restrictions.between("createdAt", interval.getStart().toDate(), interval.getEnd().toDate()));
		List<Task> tasks = criteria.list();
		return tasks;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Task findById(long id) {
		Session session = sessionFactory.getCurrentSession();
		Task task = (Task) session.get(Task.class, id);
		return task;
	}

	/**
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	**/
}
