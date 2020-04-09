package com.techmahindra.taskallocation.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.User;
import com.techmahindra.taskallocation.responses.OperationResponse;
import com.techmahindra.taskallocation.service.TaskService;
import com.techmahindra.taskallocation.service.UserService;
import com.techmahindra.taskallocation.validators.TaskValidator;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;

	@Autowired
	private TaskValidator taskValidator;
	
	/*
	 * @Autowired TaskStatusService taskStatusService;
	 */

	@Autowired
	UserService userService;

	@GetMapping("/getTasks")
	public OperationResponse getTasks(@RequestHeader(name = "securityKey") String securityKey ){

		User user = userService.findUserBySecurityKey(securityKey);

		OperationResponse oper = new OperationResponse();
		if(user == null) {
			oper.setOperValidity("failure");
			oper.setDescription("User is not valid!!");
			return oper;
		}

		//System.out.println(userId);

		oper.setOperValidity("success");
		oper.setDescription("Tasks for User with id "+user.getId());
		oper.setResult(taskService.getTasks(user));
		return oper;
	}


	@PostMapping("/addTask")
	public OperationResponse saveTask(@RequestHeader(name="securitykey") String securityKey, 
			@Valid @RequestBody Task task,
			BindingResult bindingResult){

		User user = userService.findUserBySecurityKey(securityKey);

		OperationResponse oper = new OperationResponse();
		if(user == null) {
			oper.setOperValidity("failure");
			oper.setDescription("User is not valid!!");
			return oper;
		}
		
		taskValidator.validate(task, bindingResult, true);
		
		if(bindingResult.hasErrors()) {
			oper.setOperValidity("failure");
			oper.setDescription("Task Data is not valid!!");
			oper.setResult(bindingResult.toString());
			return oper;
		}
		
		task.setCreatedBy(user.getgID());
		task.setUpdatedBy(user.getgID());

		task=taskService.saveTask(user,task);

		System.out.println(task);

		oper.setOperValidity("success");
		oper.setDescription("Task saved for user with id "+user.getId());
		oper.setResult(task);


		return oper;
	}

	/*
	 * @PostMapping("/taskStatus/add") public TaskStatus saveTaskStatus(@RequestBody
	 * TaskStatus taskStatus){ System.out.println(taskStatus); return
	 * taskStatusService.saveTaskStatus(taskStatus); }
	 * 
	 * @GetMapping("/taskStatus") public List<TaskStatus> getTaskStatus(){ return
	 * taskStatusService.getAllTaskStatus(); }
	 */
}
