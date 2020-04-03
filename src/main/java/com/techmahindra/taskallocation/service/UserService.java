package com.techmahindra.taskallocation.service;

import com.techmahindra.taskallocation.models.User;

public interface UserService {
	
	public User saveUser(User user);

	public User findByUserName(String userName);
	
	public void sendMail(String email, String subject, String message);

}
