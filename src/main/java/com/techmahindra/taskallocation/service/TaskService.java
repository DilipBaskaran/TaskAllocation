package com.techmahindra.taskallocation.service;

import java.util.List;

import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.TaskStatus;
import com.techmahindra.taskallocation.models.User;

public interface TaskService {

	public Task findById(Long id);
	public Task saveTask(Task task);
	public List<Task> getMyTasks(User user);
	public List<Task> getAllTasks(User user);
	public List<Task> getAllUsersTasks(User user);
	public List<Task> getAllUsersAllTasks(User user);
	public List<Task> getAllNonCompletedTasks(User user);
	public List<Task> getAllTasksWithStatus(User user,TaskStatus taskStatus);
	
}