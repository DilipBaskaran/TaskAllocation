package com.techmahindra.taskallocation.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techmahindra.taskallocation.dao.UserRepository;
import com.techmahindra.taskallocation.models.User;

@Service("userService")
public class UserServiceImpl implements UserDetailsService, UserService{

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Autowired
	private EmailSenderService emailSenderService;

	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findUserBygID(userName);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getgID(), user.getPassword(), getAuthority());
	}

	private List getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
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

}
