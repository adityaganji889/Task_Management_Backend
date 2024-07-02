package com.taskmanagement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.taskmanagement.dto.CommentPostRequestDTO;
import com.taskmanagement.entities.Comment;
import com.taskmanagement.entities.Task;
import com.taskmanagement.entities.User;
import com.taskmanagement.enums.TaskStatus;

public interface UserService {

    UserDetailsService userDetailService();
    
    User getUserInfo();
    
    List<Task> getAllTasksByUser();
    
    boolean updateTask(Long id, String status);
    
    TaskStatus mapStringToTaskStatus(String status);
    
    Comment createComment(CommentPostRequestDTO commentRequest);

    List<Comment> getCommentsByTask(Long taskId);
    
    Optional<Task> getTask(Long id);
}
