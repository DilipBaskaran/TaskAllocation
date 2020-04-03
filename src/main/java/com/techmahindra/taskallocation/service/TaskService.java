package com.techmahindra.taskallocation.service;

import java.util.List;

import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.User;

public interface TaskService {

	public List<Task> getTasks(Long userId);
	public Task saveTask(Long userId, Task task);
}
