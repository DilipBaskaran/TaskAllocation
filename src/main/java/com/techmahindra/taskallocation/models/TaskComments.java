package com.techmahindra.taskallocation.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class TaskComments {
	
	@Id
	@GeneratedValue
	private Long commentId;	
	
	@NotBlank(message = "Cannot be blank")
	private String comment;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	private Task task;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User commentedBy;
	
	@UpdateTimestamp
	private LocalDateTime commentedDate;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(User commentedBy) {
		this.commentedBy = commentedBy;
	}

	public LocalDateTime getCommentedDate() {
		return commentedDate;
	}

	public void setCommentedDate(LocalDateTime commentedDate) {
		this.commentedDate = commentedDate;
	}

	public TaskComments(String comment, Task task, User commentedBy, LocalDateTime commentedDate) {
		super();
		this.comment = comment;
		this.task = task;
		this.commentedBy = commentedBy;
		this.commentedDate = commentedDate;
	}
	
	
	public TaskComments() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
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
		TaskComments other = (TaskComments) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		return true;
	}

	
	

}
