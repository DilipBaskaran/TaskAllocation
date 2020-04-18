package com.techmahindra.taskallocation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.TaskComments;

public interface TaskCommentsRepository extends JpaRepository<TaskComments, Long> {
	public List<TaskComments> findByTask(Task task);
}
