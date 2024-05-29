package com.taskmanagement.dto;

import lombok.Data;

@Data
public class UserInfoJSONResponseDTO {

	private String message;
	private boolean success;
	private UserInfoResponseDTO userInfo;
	
}
