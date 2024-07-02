package com.taskmanagement.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagement.dto.CommentPostRequestDTO;
import com.taskmanagement.dto.CommentPostResponseDTO;
import com.taskmanagement.dto.CommentsListResponseDTO;
import com.taskmanagement.dto.TaskEditStatusRequestDTO;
import com.taskmanagement.dto.TaskManipulationResponseDTO;
import com.taskmanagement.dto.TaskPostResponseDTO;
import com.taskmanagement.dto.TasksListResponseDTO;
import com.taskmanagement.dto.UserInfoJSONResponseDTO;
import com.taskmanagement.dto.UserInfoResponseDTO;
import com.taskmanagement.entities.Comment;
import com.taskmanagement.entities.Task;
import com.taskmanagement.entities.User;
import com.taskmanagement.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/getUserInfo")
	public ResponseEntity<UserInfoJSONResponseDTO> getLoggedInUserInfo () {	
		User user = userService.getUserInfo();
		UserInfoResponseDTO userInfo = new UserInfoResponseDTO();
		UserInfoJSONResponseDTO response = new UserInfoJSONResponseDTO();
		if(user!=null) {
			userInfo.setId(user.getId());
			userInfo.setEmail(user.getEmail());
			userInfo.setName(user.getName());
		    userInfo.setRole(user.getRole());
		    response.setMessage("Logged in user info fetched successfully.");
		    response.setSuccess(true);
		    response.setUserInfo(userInfo);
		    return ResponseEntity.ok(response);
		}
		else {
			response.setMessage("Failed to fetch logged in user info.");
		    response.setSuccess(false);
		    response.setUserInfo(null);
		    return new ResponseEntity<UserInfoJSONResponseDTO>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAllTasksOfUser")
	public ResponseEntity<TasksListResponseDTO> getAllTasksOfUser() {
		List<Task> tasks = userService.getAllTasksByUser();
		TasksListResponseDTO response = new TasksListResponseDTO();
		if (tasks.size()!=0) {
			response.setSuccess(true);
			response.setMessage("All tasks of user: " + tasks.get(0).getUser().getName() +" fetched successfully.");
			response.setTasks(tasks);
			return new ResponseEntity<TasksListResponseDTO>(response, HttpStatus.OK);
		} else {
			response.setSuccess(false);
			response.setMessage("No tasks to display.");
			response.setTasks(null);
			return new ResponseEntity<TasksListResponseDTO>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/updateTask")
	public ResponseEntity<TaskManipulationResponseDTO> editTask(@RequestBody TaskEditStatusRequestDTO taskRequest){
		boolean isUpdated = userService.updateTask(taskRequest.getId(),taskRequest.getStatus());
		TaskManipulationResponseDTO response = new TaskManipulationResponseDTO();
		if(isUpdated) {
			response.setMessage("Task with id : "+taskRequest.getId()+" is updated successfully.");
			response.setSuccess(true);
			return new ResponseEntity<TaskManipulationResponseDTO>(response, HttpStatus.OK);
		}
		else {
			response.setMessage("Task with id : "+taskRequest.getId()+" is not found to update.");
			response.setSuccess(false);
			return new ResponseEntity<TaskManipulationResponseDTO>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/createNewComment")
	public ResponseEntity<CommentPostResponseDTO> createNewComment(@RequestBody CommentPostRequestDTO commentRequest) {
		Comment comment = userService.createComment(commentRequest);
		CommentPostResponseDTO response = new CommentPostResponseDTO();
		if (comment != null) {
			response.setSuccess(true);
			response.setMessage("New Comment created successfully for user : "+comment.getUser().getName());
			response.setComment(comment);
			return new ResponseEntity<CommentPostResponseDTO>(response, HttpStatus.OK);
		} else {
			response.setSuccess(false);
			response.setMessage("Failed to create new comment.");
			response.setComment(null);
			return new ResponseEntity<CommentPostResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAllCommentsOfTask/{id}")
	public ResponseEntity<CommentsListResponseDTO> getAllCommentsOfTask(@PathVariable("id") Long id) {
		List<Comment> comments = userService.getCommentsByTask(id);
		CommentsListResponseDTO response = new CommentsListResponseDTO();
		if (comments.size()!=0) {
			response.setSuccess(true);
			response.setMessage("All comments of task: " + id +" fetched successfully.");
			response.setComments(comments);
			return new ResponseEntity<CommentsListResponseDTO>(response, HttpStatus.OK);
		} else {
			response.setSuccess(false);
			response.setMessage("No comments to display.");
			response.setComments(null);
			return new ResponseEntity<CommentsListResponseDTO>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getTaskByUser/{id}")
	public ResponseEntity<TaskPostResponseDTO> getTask(@PathVariable("id") Long id){
		Optional<Task> task = userService.getTask(id);
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
	
}
