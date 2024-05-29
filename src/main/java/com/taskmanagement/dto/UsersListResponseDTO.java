package com.taskmanagement.dto;

import java.util.List;

import com.taskmanagement.entities.User;

import lombok.Data;

@Data
public class UsersListResponseDTO {

	private String message;
	private boolean success;
	private List<User> data;
	
}