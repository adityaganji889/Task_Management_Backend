package com.taskmanagement.dto;



import com.taskmanagement.entities.User;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
	
	private User user;
	private String token;
	private String refreshToken;
}
