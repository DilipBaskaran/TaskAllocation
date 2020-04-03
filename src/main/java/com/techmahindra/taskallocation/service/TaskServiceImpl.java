package com.techmahindra.taskallocation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmahindra.taskallocation.dao.TaskRepository;
import com.techmahindra.taskallocation.dao.TaskStatusRepository;
import com.techmahindra.taskallocation.dao.UserRepository;
import com.techmahindra.taskallocation.exceptions.ResourceNotFoundException;
import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.TaskStatus;
import com.techmahindra.taskallocation.models.User;

@Service
public class TaskServiceImpl implements TaskService {

	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	TaskStatusRepository taskStatusRepository;
	
	@Autowired
	UserRepository userRepository;

	
	@Override
	public List<Task> getTasks(Long userId) {
	
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId.toString()));
		
		return taskRepository.findAllTaskByUser(user);
		
	}

	@Override
	public Task saveTask(Long userId, Task task) {
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId.toString()));
		//TaskStatus taskStatus = taskStatusRepository.findById(task.getStatus().getId()).orElseThrow(()->new ResourceNotFoundException("TaskStatus", "id", ""+task.getStatus().getId()));
		
		//user.addTask(task);
		/*
		 * task.setUser(user); task.setStatus(taskStatus); taskStatus.addTask(task);
		 */
		
		userRepository.save(user);
		/*
		 * taskStatusRepository.save(taskStatus); taskRepository.save(task);
		 */
		
		return task;
	
	}

}
