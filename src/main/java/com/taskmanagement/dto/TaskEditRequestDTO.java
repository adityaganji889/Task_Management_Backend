package com.taskmanagement.dto;

import java.util.Date;

import com.taskmanagement.enums.TaskStatus;

import lombok.Data;

@Data
public class TaskEditRequestDTO {

	private String title;
	private String description;
	private Date dueDate;
	private String priority;
	private Long userId;
	private String taskStatus;
}
