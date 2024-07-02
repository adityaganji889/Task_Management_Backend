package com.taskmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.taskmanagement.entities.User;
import com.taskmanagement.enums.UserRole;
import com.taskmanagement.repositories.UserRepository;


@SpringBootApplication
public class TaskManagementApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		User adminAccount = userRepository.findByRole(UserRole.ADMIN);
		if(adminAccount == null) {
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setName("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setRole(UserRole.ADMIN);
			userRepository.save(user);
		}
	}
    
}
