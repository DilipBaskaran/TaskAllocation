package com.techmahindra.taskallocation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmahindra.taskallocation.models.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

	Priority findBypriorityKey(String priorityKey);
}
