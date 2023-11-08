package com.task_spcore.base.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Entity
@Table(name = "Task_Details")
@Scope(value = "prototype")
public class Task {

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO )
	private int id;
	private String description;
	@CreationTimestamp
	private LocalDateTime createdDT;
	@UpdateTimestamp
	private LocalDateTime completedDT;
	private String assignedStatus;
	private String completedStatus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getCreatedDT() {
		return createdDT;
	}
	public void setCreatedDT(LocalDateTime createdDT) {
		this.createdDT = createdDT;
	}
	public LocalDateTime getCompletedDT() {
		return completedDT;
	}
	public void setCompletedDT(LocalDateTime completedDT) {
		this.completedDT = completedDT;
	}
	public String getAssignedStatus() {
		return assignedStatus;
	}
	public void setAssignedStatus(String assignedStatus) {
		this.assignedStatus = assignedStatus;
	}
	public String getCompletedStatus() {
		return completedStatus;
	}
	public void setCompletedStatus(String completedStatus) {
		this.completedStatus = completedStatus;
	}
	
	
}
