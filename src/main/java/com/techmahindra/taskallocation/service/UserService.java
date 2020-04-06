package com.techmahindra.taskallocation.service;

import com.techmahindra.taskallocation.models.User;

public interface UserService {
	
	public User saveUser(User user);
	
	public User updateUser(User user);
	
	public User findById(long id);

	public User findByUserName(String userName);
	
	public void sendMail(String email, String subject, String message);
	
	public User findUserBySecurityKey(String securityKey);

}
