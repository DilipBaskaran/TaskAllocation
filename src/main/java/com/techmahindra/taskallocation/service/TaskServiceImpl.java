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
	
	/*
	 * @Autowired TaskStatusRepository taskStatusRepository;
	 */
	
	@Autowired
	UserRepository userRepository;

	
	@Override
	public List<Task> getTasks(User user) {
		
		return taskRepository.findAllTaskByUser(user);
		
	}

	@Override
	public Task saveTask(User user, Task task) {
		
		//task.setId(null);
		user.addTask(task);
		task.setUser(user);
		 
		userRepository.save(user);
		
		//task = taskRepository.save(task);
		
		return task;
	
	}

}
