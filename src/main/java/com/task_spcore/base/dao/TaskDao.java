package com.task_spcore.base.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.task_spcore.base.config.Config;
import com.task_spcore.base.entity.Task;
import com.task_spcore.base.entity.User;


@Component
public class TaskDao {

	EntityManager manager;//=(EntityManager)context.getBean("manager");
	EntityTransaction transaction;//=(EntityTransaction)context.getBean("transaction");
	
	
	@Autowired
	public TaskDao(@Qualifier("manager")  EntityManager em) {
		this.manager = em;
		this.transaction = em.getTransaction();
	}
	
	
	public Task saveTask(Task task)
	{
		transaction.begin();
		manager.persist(task);
		transaction.commit();
		return task;
	}
	public Task updateTask(Task task)
	{
		transaction.begin();
		manager.merge(task);
		transaction.commit();
		return task;
	}
	public Task updateTask1(Task task)
	{
		if(!transaction.isActive())
		{
			transaction.begin();
		}
		manager.merge(task);
	
		return task;
	}
	public List<Task> allTasks()
	{
		String jpql="from Task";
		Query query=manager.createQuery(jpql);
		List<Task> users=query.getResultList();
		if(users!=null && users.size()>0)
		{
			return users;
		}
		return null;
	}
	public Task getById(int id)
	{
		String jpql="from Task where id=:id";
		Query query=manager.createQuery(jpql);
		query.setParameter("id", id);
		List<Task> users=query.getResultList();
		if(users!=null && users.size()>0)
		{
			return users.get(0);
		}
		return null;
	}
	
}
