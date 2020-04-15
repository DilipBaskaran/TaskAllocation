package com.techmahindra.taskallocation.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Priority {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String priorityKey;
	
	private String priorityValue;
	
	@JsonIgnore
	@OneToMany(mappedBy="priority")
	private List<Task> tasks;
	
	@Column(unique= true)
	private int sortOrder;
	
	Priority(){
		
	}

	public Priority(String priorityKey, String priorityValue) {
		super();
		this.priorityKey = priorityKey;
		this.priorityValue = priorityValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPriorityKey() {
		return priorityKey;
	}

	public void setPriorityKey(String priorityKey) {
		this.priorityKey = priorityKey;
	}

	public String getPriorityValue() {
		return priorityValue;
	}

	public void setPriorityValue(String priorityValue) {
		this.priorityValue = priorityValue;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(Task task) {
		if(this.tasks == null)
			this.tasks = new ArrayList<Task>();
		
		this.tasks.add(task);
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
		result = prime * result + ((priorityKey == null) ? 0 : priorityKey.hashCode());
		result = prime * result + ((priorityValue == null) ? 0 : priorityValue.hashCode());
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
		Priority other = (Priority) obj;
		if (priorityKey == null) {
			if (other.priorityKey != null)
				return false;
		} else if (!priorityKey.equals(other.priorityKey))
			return false;
		if (priorityValue == null) {
			if (other.priorityValue != null)
				return false;
		} else if (!priorityValue.equals(other.priorityValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Priority [id=" + id + ", priorityKey=" + priorityKey + ", priorityValue=" + priorityValue + "]";
	}
	
	
}
