package com.techmahindra.taskallocation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmahindra.taskallocation.dao.TaskTypeRepository;
import com.techmahindra.taskallocation.models.TaskType;

@Service
public class TaskTypeServiceImpl implements TaskTypeService {
	
	@Autowired
	TaskTypeRepository taskTypeRepository;

	public TaskType saveTaskStatus(TaskType taskType) {
		return taskTypeRepository.save(taskType);
	}

	@Override
	public List<TaskType> getAllTaskType() {
		// TODO Auto-generated method stub
		return taskTypeRepository.findAll();
	}

	@Override
	public TaskType findByTypeKey(String typeKey) {
		// TODO Auto-generated method stub
		return taskTypeRepository.findBytypeKey(typeKey);
	}

	@Override
	public TaskType findById(Long id) {
		// TODO Auto-generated method stub
		Optional<TaskType> optionalType = taskTypeRepository.findById(id);
		return optionalType.isPresent()?optionalType.get():null;
	}

}
