package com.techmahindra.taskallocation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techmahindra.taskallocation.models.TaskType;

public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {
	TaskType findBytypeKey(String typeKey);
}
