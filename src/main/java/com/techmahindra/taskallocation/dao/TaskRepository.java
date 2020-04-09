package com.techmahindra.taskallocation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	public List<Task> findAllTaskByUser(User user);

}
