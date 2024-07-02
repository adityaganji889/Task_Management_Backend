package com.taskmanagement.services;

import java.util.List;
import java.util.Optional;

import com.taskmanagement.entities.User;
import com.taskmanagement.enums.TaskStatus;
import com.taskmanagement.dto.CommentPostRequestDTO;
import com.taskmanagement.dto.TaskEditRequestDTO;
import com.taskmanagement.dto.TaskPostRequestDTO;
import com.taskmanagement.entities.Comment;
import com.taskmanagement.entities.Task;

public interface AdminService {
	
	List<User> getAllUsers();
	
	Task createTask(TaskPostRequestDTO taskRequest);

	List<Task> getAllTasks();
	
	boolean deleteTask(Long id);
	
	Optional<Task> getTask(Long id);
	
	boolean updateTask(Long id, TaskEditRequestDTO taskRequest);
	
	TaskStatus mapStringToTaskStatus(String status);
	
	List<Task> searchTaskByTitle(String title);
	
}
