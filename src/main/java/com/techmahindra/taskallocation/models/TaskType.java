package com.techmahindra.taskallocation.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TaskType {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique= true)
	private String typeKey;
	
	private String typeValue;
	
	@Column(unique= true)
	private int sortOrder;
	
	@JsonIgnore
	@OneToMany(mappedBy ="taskType",fetch = FetchType.EAGER)
	private List<Task> tasks;
	
	
	public TaskType() {
		
	}

	public TaskType(String statusName) {
		super();
		this.typeValue = statusName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
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


	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
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
		result = prime * result + ((typeKey == null) ? 0 : typeKey.hashCode());
		result = prime * result + ((typeValue == null) ? 0 : typeValue.hashCode());
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
		TaskType other = (TaskType) obj;
		if (typeKey == null) {
			if (other.typeKey != null)
				return false;
		} else if (!typeKey.equals(other.typeKey))
			return false;
		if (typeValue == null) {
			if (other.typeValue != null)
				return false;
		} else if (!typeValue.equals(other.typeValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskStatus [id=" + id + ", typeKey=" + typeKey + ", typeValue=" + typeValue + "]";
	}
	
	

}
