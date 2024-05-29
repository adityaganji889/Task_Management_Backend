package com.taskmanagement.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.taskmanagement.entities.Task;
import com.taskmanagement.entities.User;
import com.taskmanagement.enums.TaskStatus;

public interface UserService {

    UserDetailsService userDetailService();
    
    User getUserInfo();
    
    List<Task> getAllTasksByUser();
    
    boolean updateTask(Long id, String status);
    
    TaskStatus mapStringToTaskStatus(String status);
        
}
