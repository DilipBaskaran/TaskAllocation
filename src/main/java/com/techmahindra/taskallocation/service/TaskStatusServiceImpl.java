package com.techmahindra.taskallocation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmahindra.taskallocation.dao.TaskStatusRepository;
import com.techmahindra.taskallocation.models.TaskStatus;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {

	@Autowired
	TaskStatusRepository taskStatusRepository;
	
	public TaskStatus saveTaskStatus(TaskStatus taskStatus) {
		System.out.println(taskStatus);
		return taskStatusRepository.save(taskStatus);
	}

	@Override
	public List<TaskStatus> getAllTaskStatus() {
		// TODO Auto-generated method stub
		return taskStatusRepository.findAll();
	}

}
