package com.task_spcore.base.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "com.task_spcore.base")
public class Config {
	private static EntityManagerFactory factory;
	

	@Bean(value = "factory")
	public EntityManagerFactory getFactory() {
		factory = Persistence.createEntityManagerFactory("spcore");
		return factory;
	}

	@Bean(value = "manager")
	@Scope(value = "prototype")
	public EntityManager getManager() {
		return getFactory().createEntityManager();
	}

	
}
