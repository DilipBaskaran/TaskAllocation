package com.techmahindra.taskallocation.service;

import java.util.List;

import com.techmahindra.taskallocation.models.User;

public interface UserService {
	
	public User saveUser(User user);
	
	public User updateUser(User user);
	
	public User findById(long id);

	public User findByUserName(String userName);
	
	public User findByEmail(String email);
	
	public void sendMail(String email, String subject, String message);
	
	public User findUserBySecurityKey(String securityKey);
	
	public List<User> findAllUsersByAdmin(String adminManager);
	
	public List<User> findDirectUsersByAdmin(String adminManager);
	
	public List<User> findSuperAdmins();
	
	public void deleteUser(User user);
	
	public List<User> findBynameContaining(String name);
	
	public List<User> findBygIDContining(String gID);
	
	public List<User> findByAdminAndNameOrGIdContaining(String adminEmail,String name, String gID);
	

}
