package com.techmahindra.taskallocation.service;

import java.util.Arrays;
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

	//@Autowired
	//private PasswordEncoder passwordEncoder;


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
	public List<User> findUsersByAdmin(String adminManager) {
		// TODO Auto-generated method stub
		return userRepository.findUsersByadminManager(adminManager);
	}

}
