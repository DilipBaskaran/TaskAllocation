package com.techmahindra.taskallocation.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.techmahindra.taskallocation.dao.UserRepository;
import com.techmahindra.taskallocation.models.User;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Override
	public User saveUser(User user) {
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	@Override
	public User updateUser(User user) {
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User findByUserName(String userName) {
		return userRepository.findUserBygIDIgnoreCase(userName.toLowerCase());
	}


	@Override
	public void sendMail(String email,String subject, String message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(email);
		mailMessage.setSubject(subject);
		mailMessage.setFrom("skytestcampaigns@gmail.com");
		mailMessage.setText(message);

		emailSenderService.sendEmail(mailMessage);
	}

	@Override
	public User findById(long id) {

		Optional<User> optionalUser =userRepository.findById(id); 

		return optionalUser.isPresent()?optionalUser.get():null;

	}
	@Override
	public User findUserBySecurityKey(String securityKey) {
		return userRepository.findUserBysecurityKey(securityKey);
	}

	@Override
	public List<User> findAllUsersByAdmin(User adminManager) {

		List<User> userList  = new ArrayList<User>();
		
		Queue<User> queue = new LinkedList<User>();
		queue.add(adminManager);
		while(!queue.isEmpty()) {
			User user = queue.remove();
			//System.out.println("Manager:"+user);
			List<User> users = userRepository.findUsersByadminManagerIgnoreCase(user.getEmail());
			for(User userRec : users) {
				userList.add(userRec);
				//System.out.println("user:"+userRec);
				if(userRec.getIsAdmin() || userRec.getIsSuperAdmin()) {
					queue.add(userRec);
				}
			}
		}

		return userList;
	}

	public List<User> findDirectUsersByAdmin(String adminManager) {
		List<User> users = userRepository.findUsersByadminManagerIgnoreCase(adminManager);
		return users;
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findUserByemailIgnoreCase(email.toLowerCase());
	}

	@Override
	public List<User> findSuperAdmins() {
		return userRepository.findUsersByisSuperAdmin(true);
	}

	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Override
	public List<User> findBynameContaining(String name) {
		return userRepository.findBynameContainingIgnoreCase(name);
	}

	@Override
	public List<User> findBygIDContining(String gID) {
		return userRepository.findBygIDContainingIgnoreCase(gID);
	}

	@Override
	public List<User> findByAdminAndNameOrGIdContaining(String adminEmail, String name, String gID) {
		return userRepository.findByAdminAndNameOrGIdContaining(adminEmail, name, gID);
	}

}
