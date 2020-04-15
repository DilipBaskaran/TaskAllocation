package com.techmahindra.taskallocation.service;

import java.util.List;

import com.techmahindra.taskallocation.models.Priority;

public interface PriorityService {
	
	public Priority findById(Long id);
	
	public Priority findBypriorityKey(String priorityKey);
	
	public List<Priority> findAll();

}
