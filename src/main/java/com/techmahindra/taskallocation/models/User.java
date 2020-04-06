package com.techmahindra.taskallocation.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id	
	@GeneratedValue
	private long id;

	@NotNull(message="Name cannot be empty")
	private String name;

	@Column(unique = true)
	@NotNull(message="gID cannot be empty")
	private String gID;

	@Column(unique = true)
	@NotBlank(message="Email cannot be empty")
	private String email;
		
	//@NotBlank(message="Admin Manager cannot be empty")
	private String adminManager;

	@NotNull(message="isActive cannot be empty")
	private Boolean isActive;

	@NotNull(message="isSuperAdmin cannot be empty")
	private Boolean isSuperAdmin;

	@NotNull(message="isAdmin cannot be empty")
	private Boolean isAdmin;

	@NotNull(message="isCandidate cannot be empty")
	private Boolean isCandidate;

	@JsonIgnore
	@NotBlank(message="Password cannot be empty")
	private String password;

	@JsonIgnore
	private String randomNo; // for Sending OTP to user in email


	@Column(name = "created_by")
	@GeneratorType(
			type = LoggedUser.class,
			when = GenerationTime.INSERT
			)
	private String createdBy;

	@CreationTimestamp
	private LocalDateTime createDateTime;


	@Column(name = "updated_by")
	@GeneratorType(
			type = LoggedUser.class,
			when = GenerationTime.ALWAYS
			)
	private String updatedBy;

	@UpdateTimestamp
	private LocalDateTime updateDateTime;


	/*
	 * @JsonIgnore
	 * 
	 * @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade =
	 * CascadeType.ALL) private List<Task> tasks;
	 */


	public User() {

	}

	public User(@NotNull(message = "Name cannot be empty") String name,
			@NotNull(message = "gID cannot be empty") String gID,
			@NotBlank(message = "Email cannot be empty") String email,
			@NotBlank(message = "Admin Manager cannot be empty") String adminManager,
			@NotNull(message = "isActive cannot be empty") Boolean isActive,
			@NotNull(message = "isSuperAdmin cannot be empty") Boolean isSuperAdmin,
			@NotNull(message = "isAdmin cannot be empty") Boolean isAdmin,
			@NotNull(message = "isCandidate cannot be empty") Boolean isCandidate) {
		super();
		this.name = name;
		this.gID = gID;
		this.email = email;
		this.adminManager = adminManager;
		this.isActive = isActive;
		this.isSuperAdmin = isSuperAdmin;
		this.isAdmin = isAdmin;
		this.isCandidate = isCandidate;
	}


	public User(long id, @NotNull(message = "User Name cannot be empty") String user_name,
			@NotNull(message = "Password cannot be empty") String password) {
		super();
		this.id = id;
		this.gID = user_name;
		this.password = password;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAdminManager() {
		return adminManager;
	}

	public void setAdminManager(String adminManager) {
		this.adminManager = adminManager;
	}

	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsSuperAdmin() {
		return isSuperAdmin;
	}
	public void setIsSuperAdmin(Boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Boolean getIsCandidate() {
		return isCandidate;
	}
	public void setIsCandidate(Boolean isCandidate) {
		this.isCandidate = isCandidate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRandomNo() {
		return randomNo;
	}

	public void setRandomNo(String randomNo) {
		this.randomNo = randomNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gID == null) ? 0 : gID.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((isAdmin == null) ? 0 : isAdmin.hashCode());
		result = prime * result + ((isCandidate == null) ? 0 : isCandidate.hashCode());
		result = prime * result + ((isSuperAdmin == null) ? 0 : isSuperAdmin.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (isAdmin == null) {
			if (other.isAdmin != null)
				return false;
		} else if (!isAdmin.equals(other.isAdmin))
			return false;
		if (isCandidate == null) {
			if (other.isCandidate != null)
				return false;
		} else if (!isCandidate.equals(other.isCandidate))
			return false;
		if (isSuperAdmin == null) {
			if (other.isSuperAdmin != null)
				return false;
		} else if (!isSuperAdmin.equals(other.isSuperAdmin))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", gID=" + gID + ", email=" + email + ", adminManager="
				+ adminManager + ", isActive=" + isActive + ", isSuperAdmin=" + isSuperAdmin + ", isAdmin=" + isAdmin
				+ ", isCandidate=" + isCandidate + ", createdBy=" + createdBy + ", createDateTime=" + createDateTime
				+ ", updatedBy=" + updatedBy + ", updateDateTime=" + updateDateTime + "]";
	}

	




	/*
	 * public List<Task> getTasks() { return tasks; }
	 * 
	 * public void setTasks(List<Task> tasks) { this.tasks = tasks; }
	 * 
	 * public void addTask(Task task) { if(this.tasks == null) this.tasks = new
	 * ArrayList<Task>();
	 * 
	 * this.tasks.add(task); }
	 */






}
