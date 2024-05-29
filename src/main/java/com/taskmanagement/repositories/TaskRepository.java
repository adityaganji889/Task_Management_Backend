package com.taskmanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanagement.entities.Task;
import com.taskmanagement.entities.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	Optional<List<Task>> findAllByTitleContaining(String title);

	Optional<List<Task>> findByUser(User user);


}
