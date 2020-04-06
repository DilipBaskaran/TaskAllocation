package com.techmahindra.taskallocation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.techmahindra.taskallocation.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findUserBygID(String gID);

}