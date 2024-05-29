package com.taskmanagement.dto;

import com.taskmanagement.entities.Task;

import lombok.Data;

@Data
public class TaskPostResponseDTO {

	private String message;
	private boolean success;
	private Task task;
	
}
