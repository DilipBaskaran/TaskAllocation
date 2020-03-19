package com.techmahindra.taskallocation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.service.TaskService;

@RestController
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@GetMapping("/tasks/{userId}")
	public List<Task> getTasks(@PathVariable Long userId){
		
		System.out.println(userId);
		return taskService.getTasks(userId);
		
		
	}
}
