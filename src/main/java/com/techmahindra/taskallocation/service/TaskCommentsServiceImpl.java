package com.techmahindra.taskallocation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmahindra.taskallocation.dao.TaskCommentsRepository;
import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.TaskComments;

@Service
public class TaskCommentsServiceImpl implements TaskCommentsService {

	@Autowired
	TaskCommentsRepository taskCommentsRepository;
	
	@Override
	public TaskComments findById(Long id) {
		return taskCommentsRepository.findById(id).get();
	}

	@Override
	public List<TaskComments> findByTask(Task task) {
		return taskCommentsRepository.findByTask(task);
	}

	@Override
	public TaskComments saveTaskComment(TaskComments taskComment) {
		// TODO Auto-generated method stub
		return taskCommentsRepository.save(taskComment);
	}

	@Override
	public void deleteTaskComment(TaskComments taskComment) {
		// TODO Auto-generated method stub
		taskCommentsRepository.delete(taskComment);
	}

}
