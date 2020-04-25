package com.techmahindra.taskallocation.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Task {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String title;
	
	private String description;
	
	private LocalDate dueDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private TaskStatus taskStatus;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private TaskType taskType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Priority priority;
	
	
	private Integer timeSheetDays;
	
	private Integer eLearningCompleted;
	
	@JsonIgnore
	@OneToMany(mappedBy = "task",fetch = FetchType.EAGER)
	private List<TaskComments> taskComments;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User assignedTo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User createdBy;

	@CreationTimestamp
	private LocalDateTime createdDateTime;

	@ManyToOne(fetch=FetchType.EAGER)
	private User updatedBy;

	@UpdateTimestamp
	private LocalDateTime updatedDateTime;
	
	public Task() {
		
	}

	public Task(String title, String description, LocalDate dueDate, String status) {
		super();
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}
	
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", dueDate=" + dueDate +", assignedTo="+assignedTo.getId()
				+ ", createdBy=" + createdBy + ", createdDateTime=" + createdDateTime
				+ ", updatedBy=" + updatedBy + ", updatedDateTime=" + updatedDateTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public List<TaskComments> getTaskComments() {
		return taskComments;
	}

	public void setTaskComments(List<TaskComments> taskComments) {
		this.taskComments = taskComments;
	}
	
	public void addTaskComment(TaskComments taskComment) {
		if(taskComments == null) {
			taskComments = new ArrayList<TaskComments>();
		}
		
		taskComments.add(taskComment);
		
	}

	public Integer getTimeSheetDays() {
		return timeSheetDays;
	}

	public void setTimeSheetDays(Integer timeSheetDays) {
		this.timeSheetDays = timeSheetDays;
	}

	public Integer getELearningCompleted() {
		return eLearningCompleted;
	}

	public void setELearningCompleted(Integer eLearningCompleted) {
		this.eLearningCompleted = eLearningCompleted;
	}

	
	
}
