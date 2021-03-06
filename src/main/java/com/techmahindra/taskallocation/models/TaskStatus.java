package com.techmahindra.taskallocation.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TaskStatus {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique= true)
	private String statusKey;
	
	private String statusValue;
	
	@Column(unique= true)
	private int sortOrder;
	
	@JsonIgnore
	@OneToMany(mappedBy ="taskStatus",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Task> tasks;
	
	
	public TaskStatus() {
		
	}

	public TaskStatus(String statusName) {
		super();
		this.statusValue = statusName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public List<Task> getTasks() {
		return tasks;
	}
	
	public void addTask(Task task) {
		if(this.tasks == null)
			this.tasks = new ArrayList<Task>();
		
		this.tasks.add(task);
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}


	public String getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(String statusKey) {
		this.statusKey = statusKey;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}

	public void setOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((statusKey == null) ? 0 : statusKey.hashCode());
		result = prime * result + ((statusValue == null) ? 0 : statusValue.hashCode());
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
		TaskStatus other = (TaskStatus) obj;
		if (statusKey == null) {
			if (other.statusKey != null)
				return false;
		} else if (!statusKey.equals(other.statusKey))
			return false;
		if (statusValue == null) {
			if (other.statusValue != null)
				return false;
		} else if (!statusValue.equals(other.statusValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskStatus [id=" + id + ", statusKey=" + statusKey + ", statusName=" + statusValue + "]";
	}
	
	

}
