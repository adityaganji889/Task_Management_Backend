package com.taskmanagement.entities;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
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
@Table(name="Comments")
public class Comment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="content")
	private String content;
	
	@Column(name="createdAt")
	private Date createdAt;
	
	@ManyToOne(fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name="user_id",nullable=false)
	@OnDelete(action= OnDeleteAction.CASCADE)
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name="task_id",nullable=false)
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Task task;
	
	
	
}
