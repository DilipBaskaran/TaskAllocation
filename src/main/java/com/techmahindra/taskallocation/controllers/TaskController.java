package com.techmahindra.taskallocation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.TaskStatus;
import com.techmahindra.taskallocation.models.User;
import com.techmahindra.taskallocation.service.TaskService;
import com.techmahindra.taskallocation.service.TaskStatusService;
import com.techmahindra.taskallocation.service.UserService;

//@RestController
@RequestMapping("/api")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@Autowired
	TaskStatusService taskStatusService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/user/{userId}/tasks")
	public List<Task> getTasks(@PathVariable Long userId){
		
		//System.out.println(userId);
		return taskService.getTasks(userId);
	}
	
	@PostMapping("/user/addUser")
	public User saveUser(@RequestBody User user){
		return userService.saveUser(user);
	}
	
	@PostMapping("/user/{userId}/tasks")
	public Task saveTask(@PathVariable Long userId, @RequestBody Task task){
		System.out.println(task);
		return taskService.saveTask(userId,task);
	}
	
	@PostMapping("/taskStatus/add")
	public TaskStatus saveTaskStatus(@RequestBody TaskStatus taskStatus){	
		System.out.println(taskStatus);
		return taskStatusService.saveTaskStatus(taskStatus);
	}
	
	@GetMapping("/taskStatus")
	public List<TaskStatus> getTaskStatus(){
		return taskStatusService.getAllTaskStatus();
	}
}
