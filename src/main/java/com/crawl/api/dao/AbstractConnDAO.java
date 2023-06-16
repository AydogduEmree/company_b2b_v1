package com.crawl.api.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractConnDAO {

	@Autowired(required = true)
	protected SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Session openNewSession() {
		return sessionFactory.openSession();
	}
	
	public StatelessSession openNewStatelessSession() {
		return sessionFactory.openStatelessSession();
	}

}
