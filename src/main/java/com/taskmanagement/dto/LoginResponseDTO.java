package com.taskmanagement.dto;


import lombok.Data;

@Data
public class LoginResponseDTO {

	private String message;
	private boolean success;
	private String token;
	private String refreshToken;
	
}