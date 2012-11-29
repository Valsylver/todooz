package fr.todooz.service;

import java.util.List;

import junit.framework.Assert;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import fr.todooz.domain.Task;

public class TaskService {
	
	private SessionFactory sessionFactory;
	
	public void save(Task task) {
		Session session = sessionFactory.openSession();
		session.save(task);
		session.close();
	}

	public void delete(Long id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("delete from Task where id=:id");
		query.setLong("id", id);
		query.executeUpdate();
		session.close();
	}

	public List<Task> findAll() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Task");
		List<Task> tasks = query.list();
		session.close();
		return tasks;
	}

	public List<Task> findByQuery(String query) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Task.class);
		criteria.add(Restrictions.or(Restrictions.like("title", "%" + query + "%"), Restrictions.like("tags", "%" + query + "%")));
		List<Task> tasks = criteria.list();
		session.close();
		return tasks;
	}

	public int count() {
		return findAll().size();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
