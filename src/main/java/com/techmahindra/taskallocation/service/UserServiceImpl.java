package com.techmahindra.taskallocation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		return userRepository.findUserBygID(userName);
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
	public List<User> findAllUsersByAdmin(String adminManager) {


		List<User> users = userRepository.findUsersByadminManager(adminManager);
		List<User> copyUsers  = new ArrayList(users);

		for(User userRec : users) {
			copyUsers.add(userRec);
			while(userRec.getIsAdmin() || userRec.getIsSuperAdmin()) {
				List<User> subordinates = userRepository.findUsersByadminManager(userRec.getEmail()); 
				if(subordinates != null)
					copyUsers.addAll(subordinates);
			}
		}

		return copyUsers;
	}

	public List<User> findDirectUsersByAdmin(String adminManager) {
		List<User> users = userRepository.findUsersByadminManager(adminManager);
		return users;
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findUserByemail(email);
	}

	@Override
	public long findCountOfUsers() {
		return userRepository.count();
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
		return userRepository.findBynameContaining(name);
	}

	@Override
	public List<User> findBygIDContining(String gID) {
		return userRepository.findBygIDContaining(gID);
	}

	@Override
	public List<User> findByAdminAndNameOrGIdContaining(String adminEmail, String name, String gID) {
		return userRepository.findByAdminAndNameOrGIdContaining(adminEmail, name, gID);
	}

}
