package com.taskmanagement.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanagement.dto.TaskEditRequestDTO;
import com.taskmanagement.dto.TaskPostRequestDTO;
import com.taskmanagement.entities.Task;
import com.taskmanagement.entities.User;
import com.taskmanagement.enums.TaskStatus;
import com.taskmanagement.enums.UserRole;
import com.taskmanagement.repositories.TaskRepository;
import com.taskmanagement.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final TaskRepository taskRepository;

	public List<User> getAllUsers() {
		List<User> users = userRepository.findAll().stream().filter(user -> user.getRole() == UserRole.USER)
				.collect(Collectors.toList());
		if (users.isEmpty()) {
			return null;
		} else {
			return users;
		}
	}

	public Task createTask(TaskPostRequestDTO taskRequest) {
		Optional<User> user = userRepository.findById(taskRequest.getUserId());
		if (user.isPresent()) {
			Task task = new Task();
			task.setTitle(taskRequest.getTitle());
			task.setDescription(taskRequest.getDescription());
			task.setPriority(taskRequest.getPriority());
			task.setDueDate(taskRequest.getDueDate());
			task.setTaskStatus(TaskStatus.INPROGRESS);
			task.setUser(user.get());
			taskRepository.save(task);
			return task;
		}
		return null;
	}

	@Override
	public List<Task> getAllTasks() {
		List<Task> tasks = taskRepository.findAll().stream().sorted(Comparator.comparing(Task::getDueDate).reversed())
				.collect(Collectors.toList());
		if (tasks.isEmpty()) {
			return null;
		} else {
			return tasks;
		}
	}

	@Override
	public boolean deleteTask(Long id) {
		// TODO Auto-generated method stub
		Optional<Task> task = taskRepository.findById(id);
		if (task.isPresent()) {
			taskRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Optional<Task> getTask(Long id) {
		// TODO Auto-generated method stub
		Optional<Task> task = taskRepository.findById(id);
		if (task.isPresent()) {
			return task;
		}
		return null;
	}

	@Override
	public boolean updateTask(Long id, TaskEditRequestDTO taskRequest) {
		// TODO Auto-generated method stub
		Optional<Task> task = taskRepository.findById(id);
		if (task.isPresent()) {
			Task t = task.get();
			User user = userRepository.findById(taskRequest.getUserId()).get();
			t.setTitle(taskRequest.getTitle());
			t.setDescription(taskRequest.getDescription());
			t.setDueDate(taskRequest.getDueDate());
			t.setPriority(taskRequest.getPriority());
			t.setUser(user);
			t.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskRequest.getTaskStatus())));
			taskRepository.save(t);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TaskStatus mapStringToTaskStatus(String status) {
		// TODO Auto-generated method stub
		return switch (status) {
		case "PENDING" -> TaskStatus.PENDING;
		case "INPROGRESS" -> TaskStatus.INPROGRESS;
		case "COMPLETED" -> TaskStatus.COMPLETED;
		case "DEFERRED" -> TaskStatus.DEFERRED;
		default -> TaskStatus.CANCELLED;
		};
	}

	@Override
	public List<Task> searchTaskByTitle(String title) {
		// TODO Auto-generated method stub
		Optional<List<Task>> tasks = taskRepository.findAllByTitleContaining(title);
		if(tasks.isEmpty()) {
			return new ArrayList<>();	
		}
		else {
		   return tasks.get();
		}
	}

}
