package com.techmahindra.taskallocation.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techmahindra.taskallocation.models.Priority;
import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.TaskStatus;
import com.techmahindra.taskallocation.models.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	public List<Task> findByAssignedTo(User assignedTo);
	public List<Task> findByAssignedToIn(List<User> assignedTo);
	public List<Task> findByAssignedToAndTaskStatus(User assignedTo,TaskStatus taskStatus);
	public List<Task> findByAssignedToAndPriority(User assignedTo,Priority priority);
	public List<Task> findByAssignedToAndTaskStatusNot(User assignedTo, TaskStatus taskStatus);
	public List<Task> findByAssignedToInAndTaskStatusNot(List<User> assignedTo, TaskStatus taskStatus);
	public List<Task> findByAssignedToAndPriorityNot(User assignedTo,Priority priority);
	
	//
	@Query("select distinct CONCAT(u.name,',',u.gID,',',t.title,',',"
			+ "CASE WHEN t.timeSheetDays IS NULL THEN '' ELSE t.timeSheetDays END,',',"
			+ "CASE WHEN t.eLearningCompleted IS NULL THEN '' ELSE t.eLearningCompleted END) from "
			+ "User u INNER JOIN Task t on t.assignedTo.id = u.id "
			+ "where t.dueDate = :date and u.isCandidate = true")
	public List<String> getCurrentMonthReport(LocalDate date);	
}
