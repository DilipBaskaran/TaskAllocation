package com.techmahindra.taskallocation.service;

import java.util.List;

import com.techmahindra.taskallocation.models.TaskStatus;

public interface TaskStatusService {
	
	public TaskStatus saveTaskStatus(TaskStatus taskStatus);

	public List<TaskStatus> getAllTaskStatus();

}
