package com.github.purnet.gamedataaccesslayer;

import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionManager {
	private static SessionManager instance = null;
	private static SessionFactory sessionFactory;
	private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<String, Session>();
	
	protected SessionManager() {
		sessionFactory = new Configuration().configure()
				.buildSessionFactory();
	}
	
	public static SessionManager getInstance(){
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}
	
	public Session createSession(String reqUuid){
		Session session = sessionFactory.openSession();
		sessions.put(reqUuid, session);
		return session;
	}
	
	public Session getSession(String reqUuid){
		Session session = sessions.get(reqUuid);
		return session;
	}
	
	public void closeSession(String reqUuid) {
		Session session = sessions.get(reqUuid);
		sessions.remove(reqUuid);
		session.close();
	}
}
