package com.techmahindra.taskallocation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmahindra.taskallocation.dao.PriorityRepository;
import com.techmahindra.taskallocation.models.Priority;

@Service
public class PriorityServiceImpl implements PriorityService {

	
	@Autowired
	PriorityRepository priorityRepository;
	
	@Override
	public Priority findById(Long id) {
		Optional<Priority> optionalPriority = priorityRepository.findById(id);
		return optionalPriority.isPresent()?optionalPriority.get():null;
	}

	@Override
	public Priority findBypriorityKey(String priorityKey) {
		return priorityRepository.findBypriorityKey(priorityKey);
	}

	@Override
	public List<Priority> findAll() {
		return priorityRepository.findAll();
	}

}
