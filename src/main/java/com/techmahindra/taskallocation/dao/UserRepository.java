package com.techmahindra.taskallocation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techmahindra.taskallocation.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findUserBygID(String gID);
	
	User findUserBysecurityKey(String securityKey);
	
	List<User> findUsersByadminManager(String adminManager);

	User findUserByemail(String email);
	
	List<User> findUsersByisSuperAdmin(boolean isSuperAdmin);
	
	List<User> findBynameContaining(String name);
	
	List<User> findBygIDContaining(String gID);
	
	@Query("select u from User u where u.adminManager LIKE %:adminEmail% and (u.name LIKE %:name% or u.gID LIKE %:gID%)")
	List<User> findByAdminAndNameOrGIdContaining(String adminEmail,String name,String gID);

}
