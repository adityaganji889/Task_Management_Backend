package com.taskmanagement.dto;

import lombok.Data;

@Data
public class CommentPostRequestDTO {

	private String content;
	private Long taskId;

}
