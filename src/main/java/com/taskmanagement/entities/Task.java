package com.taskmanagement.entities;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taskmanagement.enums.TaskStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Tasks")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String description;
	
	private Date dueDate;
	
	private String priority;
	
	private TaskStatus taskStatus;
	
	@ManyToOne(fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name="user_id",nullable=false)
	@OnDelete(action= OnDeleteAction.CASCADE)
	private User user;
	
	
	

}
