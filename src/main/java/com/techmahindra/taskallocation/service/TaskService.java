package com.techmahindra.taskallocation.service;

import java.util.List;

import com.techmahindra.taskallocation.models.Task;

public interface TaskService {

	public List<Task> getTasks(Long userId);
}
