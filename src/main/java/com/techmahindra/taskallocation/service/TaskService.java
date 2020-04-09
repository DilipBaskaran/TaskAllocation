package com.techmahindra.taskallocation.service;

import java.util.List;

import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.User;

public interface TaskService {

	public List<Task> getTasks(User user);
	public Task saveTask(User user, Task task);
}
