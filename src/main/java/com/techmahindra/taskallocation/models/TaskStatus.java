package com.techmahindra.taskallocation.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
	private long id;
	
	private String statusName;
	
	@JsonIgnore
	@OneToMany(mappedBy ="status",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Task> tasks;
	
	
	public TaskStatus() {
		
	}

	public TaskStatus(String statusName) {
		super();
		this.statusName = statusName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((statusName == null) ? 0 : statusName.hashCode());
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
		if (id != other.id)
			return false;
		if (statusName == null) {
			if (other.statusName != null)
				return false;
		} else if (!statusName.equals(other.statusName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskStatus [id=" + id + ", statusName=" + statusName + "]";
	}
	
	
	
	

}
