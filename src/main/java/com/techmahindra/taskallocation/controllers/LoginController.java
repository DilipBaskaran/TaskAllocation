package com.techmahindra.taskallocation.controllers;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmahindra.taskallocation.models.User;
import com.techmahindra.taskallocation.responses.OperationResponse;
import com.techmahindra.taskallocation.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@ResponseBody
	@PostMapping("/login")
	public OperationResponse loginReq(@RequestBody HashMap<String,String> loginMap){
		String userName = loginMap.get("username");
		String password = loginMap.get("password");
		if(userName == null || password == null)
			return new OperationResponse("failure","UserName Password combination is not accepted");
		System.out.println(userName+" "+password);
		User user = userService.findByUserName(userName);
		//System.out.println(userName); System.out.println(password);
		if (user == null  
				|| !user.getPassword().equals(
						Base64.getEncoder().encodeToString(password.getBytes())) ) 
			return new OperationResponse("failure","UserName Password combination is not accepted");



		String securityKey = Base64.getEncoder() 
				.encodeToString(
						(userName+":"+password+":"+LocalDateTime.now().toString()).getBytes());
		user.setSecurityKey(securityKey);
		userService.saveUser(user);
		return new OperationResponse("success","Login is successful!!",securityKey);


	}
	
	@ResponseBody
	@GetMapping("/logout")
	public OperationResponse logoutReq(@RequestHeader("securityKey") String securityKey){

		User user = userService.findUserBySecurityKey(securityKey);
		if(user==null) 
			return new OperationResponse("failure","Data not correct! Not a valid User!!");


		user.setSecurityKey(null);
		userService.saveUser(user);
		return new OperationResponse("success","Logout is successful!!");


	}

}
