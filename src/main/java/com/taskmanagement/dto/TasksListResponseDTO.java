package com.taskmanagement.dto;

import java.util.List;

import com.taskmanagement.entities.Task;

import lombok.Data;

@Data
public class TasksListResponseDTO {

	private String message;
	private boolean success;
	private List<Task> tasks;
}
