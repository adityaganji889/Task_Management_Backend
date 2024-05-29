package com.taskmanagement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taskmanagement.dto.TaskEditRequestDTO;
import com.taskmanagement.entities.Task;
import com.taskmanagement.entities.User;
import com.taskmanagement.enums.TaskStatus;
import com.taskmanagement.repositories.TaskRepository;
import com.taskmanagement.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final TaskRepository taskRepository;

	public UserDetailsService userDetailService() {
		// TODO Auto-generated method stub
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return userRepository.findFirstByEmail(username)
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			};
		};
	}

	public User getUserInfo() {
		User user = new User();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		var userInfo = userRepository.findFirstByEmail(userDetails.getUsername())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
		;
		if (userInfo != null) {
			user.setId(userInfo.getId());
			user.setName(userInfo.getName());
			user.setEmail(userInfo.getEmail());
			user.setRole(userInfo.getRole());
			return user;
		}
		return null;
	}

	@Override
	public List<Task> getAllTasksByUser() {
		// TODO Auto-generated method stub
		User user = this.getUserInfo();
		if (user != null) {
			Optional<List<Task>> tasks = taskRepository.findByUser(user);
			if (tasks.isEmpty()) {
				return null;
			} else {
				return tasks.get();
			}
		} else {
			return null;
		}
	}

	@Override
	public boolean updateTask(Long id, String status) {
		// TODO Auto-generated method stub
		Optional<Task> task = taskRepository.findById(id);
		if (task.isPresent()) {
			Task t = task.get();
			if (t.getUser().getId() == this.getUserInfo().getId()) {
				t.setTaskStatus(mapStringToTaskStatus(status));
				taskRepository.save(t);
				return true;
			} else {
                return false;
			}
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
}
