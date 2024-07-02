package com.taskmanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanagement.entities.Comment;
//import com.taskmanagement.entities.User;
import com.taskmanagement.entities.Task;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	Optional<List<Comment>> findByTask(Task task);
	
}
