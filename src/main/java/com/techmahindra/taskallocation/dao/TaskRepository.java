package com.techmahindra.taskallocation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmahindra.taskallocation.models.Priority;
import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.TaskStatus;
import com.techmahindra.taskallocation.models.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	public List<Task> findByAssignedTo(User assignedTo);
	public List<Task> findByAssignedToIn(List<User> assignedTo);
	public List<Task> findByAssignedToAndTaskStatus(User assignedTo,TaskStatus taskStatus);
	public List<Task> findByAssignedToAndPriority(User assignedTo,Priority priority);
	public List<Task> findByAssignedToAndTaskStatusNot(User assignedTo, TaskStatus taskStatus);
	public List<Task> findByAssignedToAndPriorityNot(User assignedTo,Priority priority);
}
