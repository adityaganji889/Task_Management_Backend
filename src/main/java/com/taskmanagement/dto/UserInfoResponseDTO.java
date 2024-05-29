package com.taskmanagement.dto;

import com.taskmanagement.enums.UserRole;

import lombok.Data;

@Data
public class UserInfoResponseDTO {

	private Long id;
	private String name;
	private String email;
	private UserRole role;
	
}
