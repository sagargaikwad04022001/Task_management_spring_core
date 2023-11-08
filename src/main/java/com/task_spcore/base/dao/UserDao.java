package com.task_spcore.base.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.task_spcore.base.entity.Task;
import com.task_spcore.base.entity.User;

@Component
public class UserDao {

	EntityManager manager;// =(EntityManager)context.getBean("manager");
	EntityTransaction transaction;// =(EntityTransaction)context.getBean("transaction");

	@Autowired

	public UserDao(@Qualifier("manager") EntityManager em) {
		this.manager = em;
		this.transaction = em.getTransaction();
	}

	public User saveUser(User user) {
		transaction.begin();
		manager.persist(user);
		transaction.commit();
		return user;
	}

	public User updateUser(User user) {
		boolean isIBegin = false;
		if (transaction.isActive()) {
			manager.merge(user);
		} else {
			transaction.begin();
			manager.merge(user);
			isIBegin = true;
		}
        
		if(isIBegin)
			transaction.commit();
		return user;
	}

	public List<User> allUsers() {
		String jpql = "from User";
		Query query = manager.createQuery(jpql);
		List<User> users = query.getResultList();
		if (users != null && users.size() > 0) {
			return users;
		}
		return null;
	}

	public User getById(int id) {
		String jpql = "from User where id=:id";
		Query query = manager.createQuery(jpql);
		query.setParameter("id", id);
		List<User> users = query.getResultList();
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	public boolean removeUser(int id) {

		User user = getById(id);
		transaction.begin();
		user.setTasks(null);
		updateUser(user);		
		manager.remove(user);
		transaction.commit();
		return true;
	}

	public boolean verifyManager(String username, String pass) {
		Query query = manager.createQuery("select u from User u where u.email=:em and u.password=:pa");
		query.setParameter("em", username);
		query.setParameter("pa", pass);
		List<User> users = query.getResultList();
		if (users != null && users.size() > 0) {
			User user = users.get(0);
			if (user.getRole().equals("Manager")) {
				return true;
			}
		}
		return false;

	}

	public boolean verifyEmployee(String username, String pass) {
		Query query = manager.createQuery("select u from User u where u.email=:em and u.password=:pa");
		query.setParameter("em", username);
		query.setParameter("pa", pass);
		List<User> users = query.getResultList();
		if (users != null && users.size() > 0) {
			User user = users.get(0);
			if (user.getRole().equals("Employee")) {
				return true;
			}
		}
		return false;

	}

	public User getEmployee(String username, String pass) {
		Query query = manager.createQuery("select u from User u where u.email=:em and u.password=:pa");
		query.setParameter("em", username);
		query.setParameter("pa", pass);
		List<User> users = query.getResultList();
		if (users != null && users.size() > 0) {
			User user = users.get(0);
			if (user.getRole().equals("Employee")) {
				return user;
			}
		}
		return null;
	}
}
