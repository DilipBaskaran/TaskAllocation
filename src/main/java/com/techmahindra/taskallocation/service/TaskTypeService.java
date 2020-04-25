package com.techmahindra.taskallocation.service;

import java.util.List;

import com.techmahindra.taskallocation.models.TaskType;

public interface TaskTypeService {
	
	public TaskType findById(Long id);
	
	public TaskType saveTaskStatus(TaskType taskType);

	public List<TaskType> getAllTaskType();
	
	public TaskType findByTypeKey(String typeKey);

}
