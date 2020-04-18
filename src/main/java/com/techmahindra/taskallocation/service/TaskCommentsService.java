package com.techmahindra.taskallocation.service;

import java.util.List;

import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.TaskComments;

public interface TaskCommentsService {
	
	public TaskComments findById(Long id);
	public List<TaskComments> findByTask(Task task);
	public TaskComments saveTaskComment(TaskComments taskComment);
	public void deleteTaskComment(TaskComments taskComment);

}
