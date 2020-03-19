package com.techmahindra.taskallocation.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(unique = true)
	private String gID;
	
	@Column(unique = true)
	private String email;
	
	private String active;
	
	private String isAdmin;
	
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private List<Task> tasks;
	
	
	public User() {
		
	}
	
	public User(String gID, String email, String active, String isAdmin) {
		super();
		this.gID = gID;
		this.email = email;
		this.active = active;
		this.isAdmin = isAdmin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getgID() {
		return gID;
	}

	public void setgID(String gID) {
		this.gID = gID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gID == null) ? 0 : gID.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((isAdmin == null) ? 0 : isAdmin.hashCode());
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
		User other = (User) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gID == null) {
			if (other.gID != null)
				return false;
		} else if (!gID.equals(other.gID))
			return false;
		if (id != other.id)
			return false;
		if (isAdmin == null) {
			if (other.isAdmin != null)
				return false;
		} else if (!isAdmin.equals(other.isAdmin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", gID=" + gID + ", email=" + email + ", active=" + active + ", isAdmin=" + isAdmin
				+ ", tasks=" + tasks + "]";
	}
		
}
