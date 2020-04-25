package com.techmahindra.taskallocation.controllers;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.techmahindra.taskallocation.models.Priority;
import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.TaskComments;
import com.techmahindra.taskallocation.models.TaskStatus;
import com.techmahindra.taskallocation.models.TaskType;
import com.techmahindra.taskallocation.models.User;
import com.techmahindra.taskallocation.responses.OperationResponse;
import com.techmahindra.taskallocation.service.PriorityService;
import com.techmahindra.taskallocation.service.TaskCommentsService;
import com.techmahindra.taskallocation.service.TaskService;
import com.techmahindra.taskallocation.service.TaskStatusService;
import com.techmahindra.taskallocation.service.TaskTypeService;
import com.techmahindra.taskallocation.service.UserService;
import com.techmahindra.taskallocation.validators.TaskValidator;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@Autowired
	TaskCommentsService taskCommentsService;

	@Autowired
	private TaskValidator taskValidator;

	@Autowired 
	TaskTypeService taskTypeService;
	
	@Autowired 
	TaskStatusService taskStatusService;

	@Autowired 
	PriorityService priorityService;

	@Autowired
	UserService userService;
	
	@GetMapping("/getAllTaskType") 
	public OperationResponse getTaskTypes(@RequestHeader(name = "securityKey") String securityKey ){
		User user = userService.findUserBySecurityKey(securityKey);
		if(user == null)
			return new OperationResponse("failure","User is not valid");

		return new OperationResponse("success",
				"Task Priorities available!!",
				taskTypeService.getAllTaskType()); 
	}

	@GetMapping("/getAllTaskStatus") 
	public OperationResponse getTaskStatus(@RequestHeader(name = "securityKey") String securityKey ){
		User reqUser = userService.findUserBySecurityKey(securityKey);
		if(reqUser == null)
			return new OperationResponse("failure","User is not valid");

		return new OperationResponse("success",
				"TaskStatus available for tasks",
				taskStatusService.getAllTaskStatus()); 
	}
	
	@GetMapping("/getAllPriority") 
	public OperationResponse getPriorities(@RequestHeader(name = "securityKey") String securityKey ){
		User reqUser = userService.findUserBySecurityKey(securityKey);
		if(reqUser == null)
			return new OperationResponse("failure","User is not valid");

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
		User reqUser = userService.findUserBySecurityKey(securityKey);
		if(reqUser == null)
			return new OperationResponse("failure","User is not valid!!");
		
		return new OperationResponse("success",
				"Tasks for User with id "+reqUser.getId(),
				taskService.getMyTasks(reqUser));
	}
	
	@GetMapping("/getAllTasks")
	public OperationResponse getAllTasks(@RequestHeader(name = "securityKey") String securityKey ){
		User reqUser = userService.findUserBySecurityKey(securityKey);
		if(reqUser == null)
			return new OperationResponse("failure","User is not valid!!");

		//System.out.println(userId);
		

		return new OperationResponse("success",
				"Tasks Available for AllUsers under User with id "+reqUser.getId(),
				taskService.getAllTasks(reqUser));
	}
	
	@GetMapping("/getUserTasks/{id}")
	public OperationResponse getUserSpecificTasks(@RequestHeader(name = "securityKey") String securityKey ,
			@PathVariable Long id){
		User adminUser = userService.findUserBySecurityKey(securityKey);
		if(adminUser == null)
			return new OperationResponse("failure","User Key is not valid!!");

		User user = userService.findById(id);
		if(user == null)
			return new OperationResponse("failure","User is not valid!!","There is no valid user with given id.");

		return new OperationResponse("success",
				"Tasks Available for AllUsers under User with id "+adminUser.getId(),
				taskService.getAllNonCompletedTasks(user));
	}
	
	@GetMapping("/getAllUsersTasks")
	public OperationResponse getAllUsersTasks(@RequestHeader(name = "securityKey") String securityKey ){
		User adminUser = userService.findUserBySecurityKey(securityKey);
		if(adminUser == null)
			return new OperationResponse("failure","User is not valid!!");

		return new OperationResponse("success",
				"Tasks Available for AllUsers under User with id "+adminUser.getId(),
				taskService.getAllUsersTasks(adminUser));
	}
	
	@GetMapping("/getAllUsersAllTasks")
	public OperationResponse getAllUsersAllTasks(@RequestHeader(name = "securityKey") String securityKey ){
		User adminUser = userService.findUserBySecurityKey(securityKey);
		if(adminUser == null)
			return new OperationResponse("failure","User is not valid!!");

		return new OperationResponse("success",
				"Tasks Available for AllUsers under User with id "+adminUser.getId(),
				taskService.getAllUsersAllTasks(adminUser));
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

		TaskType taskType = task.getTaskType();
		if(taskType.getId() == null) {
			if(taskType.getTypeKey() == null) 
				return new OperationResponse("failure","TaskType Data is not valid!!","id/typeKey need to be present");
			else 
				taskType = taskTypeService.findByTypeKey(taskType.getTypeKey());
		}else 
			taskType = taskTypeService.findById(taskType.getId());

		if(taskType == null)
			return new OperationResponse("failure", "TaskType Data is not valid!!","id/typeKey is not valid!!");

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
		else {
			String status = taskStatus.getStatusKey();
			if(status.equals("COMPLETED")) {
				String typeKey = taskType.getTypeKey();
				if(typeKey.equals("ELEARNING" )) {
					if(task.getELearningCompleted()==null)
						return new OperationResponse("failure", "Task Data is not valid!!","Task ELearningCompleted should not be empty");
				}else if(typeKey.equals("TIMESHEET"))
					if(task.getTimeSheetDays()==null)
						return new OperationResponse("failure", "Task Data is not valid!!","Task TimeSheetDays should not be empty");
			}
		}
		
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

			task.setAssignedTo(user);
		}
		

		task.setTaskType(taskType);
		task.setTaskStatus(taskStatus);
		task.setPriority(priority);
		if(isCreate) {
			task.setCreatedBy(keyUser);
			task.setUpdatedBy(keyUser);
		}else {
			taskToUpdate.setTitle(task.getTitle());
			taskToUpdate.setDescription(task.getDescription());
			taskToUpdate.setAssignedTo(task.getAssignedTo());
			taskToUpdate.setTaskType(task.getTaskType());
			taskToUpdate.setTaskStatus(task.getTaskStatus());
			taskToUpdate.setDueDate(task.getDueDate());
			taskToUpdate.setPriority(task.getPriority());
			if(task.getELearningCompleted()!=null)
				taskToUpdate.setELearningCompleted(task.getELearningCompleted());
			
			if(task.getTimeSheetDays()!=null)
				taskToUpdate.setELearningCompleted(task.getTimeSheetDays());
			
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
	
	@GetMapping("/{id}/getTaskComments")
	public OperationResponse getTasksComments(@RequestHeader(name = "securityKey") String securityKey, 
			@PathVariable Long id ){
		User reqUser = userService.findUserBySecurityKey(securityKey);
		if(reqUser == null)
			return new OperationResponse("failure","User is not valid!!","securityKey is not valid");

		Task task = taskService.findById(id);
		if(task == null)
			return new OperationResponse("failure","Task is not valid!!","id is not valid");

		return new OperationResponse("success",
				"TaskComments for Task with id "+id,
				task.getTaskComments());
	}
	
	@PostMapping("/{id}/addTaskComment")
	public OperationResponse addTasksComment(@RequestHeader(name = "securityKey") String securityKey,
			@Valid @RequestBody TaskComments taskComment, 
			@PathVariable Long id){
		User reqUser = userService.findUserBySecurityKey(securityKey);
		if(reqUser == null)
			return new OperationResponse("failure","User is not valid!!","securityKey is not valid");

		Task task = taskService.findById(id);
		if(task == null)
			return new OperationResponse("failure","Task is not valid!!","id is not valid");
		
		taskComment.setTask(task);
		task.addTaskComment(taskComment);
		taskComment.setCommentedBy(reqUser);
		taskComment = taskCommentsService.saveTaskComment(taskComment);
		return new OperationResponse("success",
				"TaskComments created for Task with id "+id,
				taskComment);
	}

	@PutMapping("/{id}/updateTaskComment")
	public OperationResponse updateTasksComment(@RequestHeader(name = "securityKey") String securityKey,
			@Valid @RequestBody TaskComments taskComment, 
			@PathVariable Long id){
		User reqUser = userService.findUserBySecurityKey(securityKey);
		if(reqUser == null)
			return new OperationResponse("failure","User is not valid!!","securityKey is not valid");

		Task task = taskService.findById(id);
		if(task == null)
			return new OperationResponse("failure","Task is not valid!!","id is not valid");
		
		TaskComments taskCommentToUpdate = taskCommentsService.findById(taskComment.getCommentId());
		if(taskCommentToUpdate == null || !task.equals(taskCommentToUpdate.getTask()))
			return new OperationResponse("failure","Task is not valid!!","Taskid/TaskCommentId is not valid");
		
		taskCommentToUpdate.setTask(task);
		taskCommentToUpdate.setCommentedBy(reqUser);
		taskCommentToUpdate.setComment(taskComment.getComment());
		task.addTaskComment(taskCommentToUpdate);
		taskComment = taskCommentsService.saveTaskComment(taskCommentToUpdate);
		return new OperationResponse("success",
				"TaskComments updated for Taskcomment with id "+taskCommentToUpdate.getCommentId(),
				taskComment);
	}
	@DeleteMapping("/deleteTaskComment/{id}")
	public OperationResponse updateTasksComment(@RequestHeader(name = "securityKey") String securityKey, 
			@PathVariable Long id){
		User reqUser = userService.findUserBySecurityKey(securityKey);
		if(reqUser == null)
			return new OperationResponse("failure","User is not valid!!","securityKey is not valid");

		TaskComments taskComment = taskCommentsService.findById(id);
		if(taskComment == null)
			return new OperationResponse("failure","TaskComment is not valid!!","TaskComment id is not valid");
		
		taskCommentsService.deleteTaskComment(taskComment);
		return new OperationResponse("success",
				"TaskComments deleted with id "+ id);
	}
}
