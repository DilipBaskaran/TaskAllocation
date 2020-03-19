package com.techmahindra.taskallocation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.techmahindra.taskallocation.dao.TaskRepository;
import com.techmahindra.taskallocation.dao.UserRepository;
import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.User;

@Service
public class TaskServiceImpl implements TaskService {

	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<Task> getTasks(Long userId) {
	
		Optional<User> optionalUser = userRepository.findById(userId);
		
		if(!optionalUser.isPresent()) {
			return new ArrayList<Task>();
		}
		
		User user = optionalUser.get();
		
		return taskRepository.findAllTaskByUser(user);
		
	}

}
