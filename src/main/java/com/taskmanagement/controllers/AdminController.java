package com.taskmanagement.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagement.dto.TaskEditRequestDTO;
import com.taskmanagement.dto.TaskManipulationResponseDTO;
import com.taskmanagement.dto.TaskPostRequestDTO;
import com.taskmanagement.dto.TaskPostResponseDTO;
import com.taskmanagement.dto.TasksListResponseDTO;
import com.taskmanagement.dto.UsersListResponseDTO;
import com.taskmanagement.entities.Task;
import com.taskmanagement.entities.User;
import com.taskmanagement.services.AdminService;
import com.taskmanagement.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {

	@Autowired
	private final AdminService adminService;

	@GetMapping("/getAllUsers")
	public ResponseEntity<UsersListResponseDTO> getAllUsers() {
		List<User> users = adminService.getAllUsers();
		UsersListResponseDTO response = new UsersListResponseDTO();
		if (users != null) {
			response.setSuccess(true);
			response.setMessage("All Users fetched successfully.");
			response.setData(users);
			return new ResponseEntity<UsersListResponseDTO>(response, HttpStatus.OK);
		} else {
			response.setSuccess(false);
			response.setMessage("No users to display.");
			response.setData(users);
			return new ResponseEntity<UsersListResponseDTO>(response, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/createNewTask")
	public ResponseEntity<TaskPostResponseDTO> createNewTask(@RequestBody TaskPostRequestDTO taskRequest) {
		Task task = adminService.createTask(taskRequest);
		TaskPostResponseDTO response = new TaskPostResponseDTO();
		if (task != null) {
			response.setSuccess(true);
			response.setMessage("New Task created successfully for user : "+task.getUser().getName());
			response.setTask(task);
			return new ResponseEntity<TaskPostResponseDTO>(response, HttpStatus.OK);
		} else {
			response.setSuccess(false);
			response.setMessage("Failed to create new task.");
			response.setTask(null);
			return new ResponseEntity<TaskPostResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAllTasks")
	public ResponseEntity<TasksListResponseDTO> getAllTasks() {
		List<Task> tasks = adminService.getAllTasks();
		TasksListResponseDTO response = new TasksListResponseDTO();
		if (tasks!= null) {
			response.setSuccess(true);
			response.setMessage("All tasks fetched successfully.");
			response.setTasks(tasks);
			return new ResponseEntity<TasksListResponseDTO>(response, HttpStatus.OK);
		} else {
			response.setSuccess(false);
			response.setMessage("No tasks to display.");
			response.setTasks(null);
			return new ResponseEntity<TasksListResponseDTO>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/deleteTask/{id}")
	public ResponseEntity<TaskManipulationResponseDTO> deleteTask(@PathVariable("id") Long id){
		Optional<Task> task = adminService.getTask(id);
		boolean isDeleted = adminService.deleteTask(id);
		TaskManipulationResponseDTO response = new TaskManipulationResponseDTO();
		if(isDeleted) {
			response.setMessage("Task with title :"+task.get().getTitle()+" is deleted successfully.");
			response.setSuccess(true);
			return new ResponseEntity<TaskManipulationResponseDTO>(response, HttpStatus.OK);
		}
		else {
			response.setMessage("Task with id :"+id+" is not found to delete.");
			response.setSuccess(false);
			return new ResponseEntity<TaskManipulationResponseDTO>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getTask/{id}")
	public ResponseEntity<TaskPostResponseDTO> getTask(@PathVariable("id") Long id){
		Optional<Task> task = adminService.getTask(id);
		TaskPostResponseDTO response = new TaskPostResponseDTO();
		if(task!=null) {
			response.setMessage("Task with id :"+task.get().getId()+" is fetched successfully.");
			response.setSuccess(true);
			response.setTask(task.get());
			return new ResponseEntity<TaskPostResponseDTO>(response, HttpStatus.OK);
		}
		else {
			response.setMessage("Task with id :"+id+" is not found to delete.");
			response.setSuccess(false);
			response.setTask(null);
			return new ResponseEntity<TaskPostResponseDTO>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/updateTask/{id}")
	public ResponseEntity<TaskManipulationResponseDTO> editTask(@PathVariable("id") Long id, @RequestBody TaskEditRequestDTO taskRequest){
		boolean isUpdated = adminService.updateTask(id,taskRequest);
		TaskManipulationResponseDTO response = new TaskManipulationResponseDTO();
		if(isUpdated) {
			response.setMessage("Task with id :"+id+" is updated successfully.");
			response.setSuccess(true);
			return new ResponseEntity<TaskManipulationResponseDTO>(response, HttpStatus.OK);
		}
		else {
			response.setMessage("Task with id :"+id+" is not found to update.");
			response.setSuccess(false);
			return new ResponseEntity<TaskManipulationResponseDTO>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/searchTasksByTitle/{title}")
	public ResponseEntity<TasksListResponseDTO> searchTasksByTitle(@PathVariable("title") String title) {
		List<Task> tasks = adminService.searchTaskByTitle(title);
		TasksListResponseDTO response = new TasksListResponseDTO();
		if (tasks.size()!=0) {
			response.setSuccess(true);
			response.setMessage("All tasks containing searched title fetched successfully.");
			response.setTasks(tasks);
			return new ResponseEntity<TasksListResponseDTO>(response, HttpStatus.OK);
		} else {
			response.setSuccess(false);
			response.setMessage("No tasks to display containing searched title.");
			response.setTasks(null);
			return new ResponseEntity<TasksListResponseDTO>(response, HttpStatus.NOT_FOUND);
		}
	}
	
}
