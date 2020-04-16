package com.techmahindra.taskallocation.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techmahindra.taskallocation.models.Priority;
import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.TaskStatus;
import com.techmahindra.taskallocation.models.User;
import com.techmahindra.taskallocation.responses.OperationResponse;
import com.techmahindra.taskallocation.service.PriorityService;
import com.techmahindra.taskallocation.service.TaskService;
import com.techmahindra.taskallocation.service.TaskStatusService;
import com.techmahindra.taskallocation.service.UserService;
import com.techmahindra.taskallocation.validators.TaskValidator;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;

	@Autowired
	private TaskValidator taskValidator;

	@Autowired 
	TaskStatusService taskStatusService;

	@Autowired 
	PriorityService priorityService;

	@Autowired
	UserService userService;

	@GetMapping("/getAllTaskStatus") 
	public OperationResponse getTaskStatus(@RequestHeader(name = "securityKey") String securityKey ){

		User user = userService.findUserBySecurityKey(securityKey);

		if(user == null)
			return new OperationResponse("failure","User is not valid");

		//System.out.println(userId);

		return new OperationResponse("success",
				"TaskStatus available for tasks",
				taskStatusService.getAllTaskStatus()); 
	}
	
	@GetMapping("/getAllPriority") 
	public OperationResponse getPriorities(@RequestHeader(name = "securityKey") String securityKey ){

		User user = userService.findUserBySecurityKey(securityKey);

		if(user == null)
			return new OperationResponse("failure","User is not valid");

		//System.out.println(userId);

		return new OperationResponse("success",
				"Task Priorities available!!",
				priorityService.findAll()); 
	}

	/*
	 * @PostMapping("/addTaskStatus") public OperationResponse
	 * saveTask(@RequestHeader(name="securitykey") String securityKey,
	 * 
	 * @Valid @RequestBody TaskStatus taskStatus, BindingResult bindingResult){
	 * 
	 * User user = userService.findUserBySecurityKey(securityKey);
	 * 
	 * OperationResponse oper = new OperationResponse(); if(user == null) {
	 * oper.setOperValidity("failure"); oper.setDescription("User is not valid!!");
	 * return oper; }
	 * 
	 * 
	 * if(bindingResult.hasErrors()) { oper.setOperValidity("failure");
	 * oper.setDescription("Data not accepted!!");
	 * oper.setResult(bindingResult.toString()); return oper; }
	 * 
	 * //System.out.println(userId);
	 * 
	 * 
	 * 
	 * taskStatus = taskStatusService.saveTaskStatus(taskStatus);
	 * 
	 * oper.setOperValidity("success"); oper.setDescription("TaskStatus Added");
	 * oper.setResult(taskStatus); return oper;
	 * 
	 * }
	 */

	@GetMapping("/getMyTasks")
	public OperationResponse getMyTasks(@RequestHeader(name = "securityKey") String securityKey ){

		User user = userService.findUserBySecurityKey(securityKey);

		if(user == null)
			return new OperationResponse("failure","User is not valid!!");

		//System.out.println(userId);

		return new OperationResponse("success",
				"Tasks for User with id "+user.getId(),
				taskService.getAllTasks(user));
	}
	
	@GetMapping("/getAllTasks")
	public OperationResponse getAllTasks(@RequestHeader(name = "securityKey") String securityKey ){

		User user = userService.findUserBySecurityKey(securityKey);

		if(user == null)
			return new OperationResponse("failure","User is not valid!!");

		//System.out.println(userId);
		

		return new OperationResponse("success",
				"Tasks for User with id "+user.getId(),
				taskService.getAllTasks(user));
	}

	@PostMapping("/addTask")
	public OperationResponse saveTask(@RequestHeader(name="securitykey") String securityKey, 
			@Valid @RequestBody Task task,
			BindingResult bindingResult){

		User adminUser = userService.findUserBySecurityKey(securityKey);
		if(adminUser == null) 
			return new OperationResponse("failure","User is not valid");

		taskValidator.validate(task, bindingResult, true);
		if(bindingResult.hasErrors())
			return new OperationResponse("failure","Task Data is not valid",bindingResult.toString());

		return addTask(adminUser,task,true);
	}

	@PutMapping("/updateTask")
	public OperationResponse updateTask(@RequestHeader(name="securitykey") String securityKey, 
			@Valid @RequestBody Task task,
			BindingResult bindingResult){

		User adminUser = userService.findUserBySecurityKey(securityKey);
		if(adminUser == null)
			return new OperationResponse("failure","User is not valid");

		taskValidator.validate(task, bindingResult, false);
		if(bindingResult.hasErrors())
			return new OperationResponse("failure","Task Data is not valid",bindingResult.toString());


		if(taskService.findById(task.getId()) == null)
			return new OperationResponse("failure",
					"Task is not valid",
					"Task id is not valid");

		return addTask(adminUser,task,false);
	}

	private OperationResponse addTask(User keyUser,Task task,boolean isCreate) {

		Task taskToUpdate = null;
		
		if(!isCreate) {
			taskToUpdate = taskService.findById(task.getId());
			if(taskToUpdate == null)
				return new OperationResponse("failure","Task Data is not valid!!","id is not valid");	
		}

		TaskStatus taskStatus = task.getTaskStatus();
		if(taskStatus.getId() == null) {
			if(taskStatus.getStatusKey() == null) 
				return new OperationResponse("failure","TaskStatus Data is not valid!!","id/statusKey need to be present");
			else 
				taskStatus = taskStatusService.findByStatusKey(taskStatus.getStatusKey());
		}else 
			taskStatus = taskStatusService.findById(taskStatus.getId());

		if(taskStatus == null)
			return new OperationResponse("failure", "TaskStatus Data is not valid!!","id/statusKey is not valid!!");
		//taskStatus.addTask(task);
		
		Priority priority = task.getPriority();		
		if(priority.getId()==null ) {
			if(priority.getPriorityKey()==null)
				return new OperationResponse("failure","Priority Data is not valid!!","id/priorityKey is not present!!");
			else 
				priority = priorityService.findBypriorityKey(priority.getPriorityKey());
		}else 
			priority = priorityService.findById(priority.getId());

		if(priority == null) 
			return new OperationResponse("failure","Priority Data is not valid!!","id/statusKey is not valid!!");


		if( task.getAssignedTo() == null || task.getAssignedTo().getId()==null) 
			task.setAssignedTo(keyUser);
		else {
			User user = task.getAssignedTo();
			user = userService.findById(user.getId());
			if(user == null)
				return new OperationResponse("failure","assignedTo Data is not valid!!","assignedTo id is not valid!!");

			//System.out.println(user);
			task.setAssignedTo(user);
		}
		

		task.setTaskStatus(taskStatus);
		task.setPriority(priority);
		if(isCreate) {
			task.setCreatedBy(keyUser);
			task.setUpdatedBy(keyUser);
		}else {
			taskToUpdate.setTitle(task.getTitle());
			taskToUpdate.setDescription(task.getDescription());
			taskToUpdate.setAssignedTo(task.getAssignedTo());
			taskToUpdate.setTaskStatus(task.getTaskStatus());
			taskToUpdate.setDueDate(task.getDueDate());
			taskToUpdate.setPriority(task.getPriority());
			taskToUpdate.setUpdatedBy(keyUser);
		}
		
		if(isCreate)
			task = taskService.saveTask(task);
		else
			task = taskService.saveTask(taskToUpdate);

		return new OperationResponse("success",
				"Task assigned to User with id : "+task.getAssignedTo().getId(),
				task);
	}
}
