package com.techmahindra.taskallocation.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmahindra.taskallocation.dao.TaskRepository;
import com.techmahindra.taskallocation.dao.TaskStatusRepository;
import com.techmahindra.taskallocation.dao.UserRepository;
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
		Optional<Task> optionalTask = taskRepository.findById(id);
		return optionalTask.isPresent()?optionalTask.get():null;
	}
	
	@Override
	public Task saveTask(Task task) {
		task = taskRepository.save(task);
		return task;
	}
	
	@Override
	public List<Task> getMyTasks(User user) {
		System.out.println(LocalDate.now().toString());
		System.out.println(taskRepository.getCurrentMonthReport(LocalDate.now()));
		return getAllNonCompletedTasks(user);
	}
	
	@Override
	public List<Task> getAllTasks(User user) {
		return taskRepository.findByAssignedTo(user);
	}
	
	@Override
	public List<Task> getAllUsersTasks(User user) {
		List<User> users = userService.findAllUsersByAdmin(user);
		users.add(user);
		TaskStatus taskStatus = taskStatusRepository.findBystatusKey("COMPLETED");
		return taskRepository.findByAssignedToInAndTaskStatusNot(users,taskStatus);
	}

	@Override
	public List<Task> getAllUsersAllTasks(User user) {
		List<User> users = userService.findAllUsersByAdmin(user);
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
