package com.taskmanagement.dto;

import java.util.List;

import com.taskmanagement.entities.Comment;

import lombok.Data;

@Data
public class CommentsListResponseDTO {

	private String message;
	private boolean success;
	private List<Comment> comments;
}
