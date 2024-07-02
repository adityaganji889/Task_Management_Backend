package com.taskmanagement.dto;

import com.taskmanagement.entities.Comment;

import lombok.Data;

@Data
public class CommentPostResponseDTO {

	private String message;
	private boolean success;
	private Comment comment;
	
}
