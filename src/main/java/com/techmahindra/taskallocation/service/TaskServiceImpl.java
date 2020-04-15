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
	
	@Autowired
	UserService userService;
	
	@Override
	public Task findById(Long id) {
		return taskRepository.findById(id).get();
	}
	
	@Override
	public Task saveTask(Task task) {
		task = taskRepository.save(task);
		return task;
	}
	
	@Override
	public List<Task> getMyTasks(User user) {
		return taskRepository.findByAssignedTo(user);
	}
	
	@Override
	public List<Task> getAllTasks(User user) {
		
		List<User> users = userService.findAllUsersByAdmin(user.getEmail());
		users.add(user);
		return taskRepository.findByAssignedToIn(users);
	}
	
	@Override
	public List<Task> getAllNonCompletedTasks(User user) {
		TaskStatus taskStatus = taskStatusRepository.findBystatusKey("COMPLETED");
		return taskRepository.findByAssignedToAndTaskStatusNot(user,taskStatus);
	}

	@Override
	public List<Task> getAllTasksWithStatus(User user,TaskStatus taskStatus){
		return taskRepository.findByAssignedToAndTaskStatus(user, taskStatus);
	}

	

}
