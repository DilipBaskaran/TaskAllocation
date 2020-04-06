package com.techmahindra.taskallocation.controllers;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmahindra.taskallocation.config.Constants;
import com.techmahindra.taskallocation.models.User;
import com.techmahindra.taskallocation.responses.ErrorResponse;
import com.techmahindra.taskallocation.responses.OperationResponse;
import com.techmahindra.taskallocation.service.UserService;
import com.techmahindra.taskallocation.validators.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;


	/*
	 * private String getCurrentUserName(){ String currentUserName = null;
	 * Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication(); if (!(authentication
	 * instanceof AnonymousAuthenticationToken)) { currentUserName =
	 * authentication.getName(); } return currentUserName; }
	 */

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());

		return "register";
	}

	@ResponseBody
	@PostMapping("/confirmuser")
	public OperationResponse confirmuser(@RequestParam("username") String userName,
			@RequestParam("uniqueno") String uniqueno, 
			Model model) {

		User user = userService.findByUserName(userName);
		//System.out.println(user);

		if(user==null || !uniqueno.equalsIgnoreCase(user.getRandomNo())) {
			
			OperationResponse oper = new OperationResponse();
			
			oper.setOperValidity("Data not correct");
			oper.setDescription("UserName & OTP combination is wrong");
			
			return oper;
		}

		model.addAttribute("username", userName);
		OperationResponse oper = new OperationResponse();
		
		oper.setOperValidity("Success");
		oper.setDescription("User Confirmation done");
		
		return oper;

	}

	@ResponseBody
	@PostMapping("/registration")
	public OperationResponse registration(@ModelAttribute("user") User user, 
			BindingResult bindingResult) {
		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			OperationResponse oper = new OperationResponse();
			
			oper.setOperValidity("Data not correct");
			oper.setDescription("User information given are wrong");
			
			return oper;
		}

		long random = Math.round(Math.random()*10000);
		
		user.setPassword("abc123");
		user.setRandomNo(""+random);
		user.setIsActive(false);
		System.out.println(user+"\nRandom Number: "+random);


		userService.saveUser(user);

		userService.sendMail("kumardilip1990b@gmail.com",
				"Complete Registration!", 
				"Enter this to change password: "+random+"--->"+user.getEmail());

		OperationResponse oper = new OperationResponse();
		
		oper.setOperValidity("Success");
		oper.setDescription("User Registration done");
		
		return oper;
	}

	@ResponseBody
	@PostMapping("/setpassword")
	public OperationResponse setPassword(@RequestParam("username") String userName,
			@RequestParam("password") String password) {
		
		OperationResponse oper = new OperationResponse();
		
		oper.setOperValidity("Data not correct");
		oper.setDescription("UserName Password combination is not accepted");
		

		if(password == null || userName==null)
			return oper;

		User user = userService.findByUserName(userName);

		if (user==null) {
			return oper;
		}
		
		System.out.println(password);
		System.out.println(Base64.getEncoder() 
                .encodeToString( password.split(",")[0].getBytes()));
		user.setPassword(Base64.getEncoder() 
                .encodeToString( password.getBytes()));
		user.setIsActive(true);
		userService.saveUser(user);

		oper.setOperValidity("Success");
		oper.setDescription("Password is set properly");
		
		return oper;
	}

	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "forgotPassword";
	}

	@ResponseBody
	@PostMapping("/forgotPassword")
	public OperationResponse forgotPassword(@ModelAttribute("username")String username,
			BindingResult error,
			Model model) {

		OperationResponse oper = new OperationResponse();
		
		oper.setOperValidity("Data not correct");
		oper.setDescription("User information given are wrong");
		
		User user = userService.findByUserName(username);

		if(user == null)
			return oper;

		model.addAttribute("username", username);
		
		long random = Math.round(Math.random()*10000);
		user.setRandomNo(""+random);
		user.setIsActive(false);
		System.out.println(user+"\nRandom Number: "+random);
		
		userService.saveUser(user);

		userService.sendMail("kumardilip1990b@gmail.com",
				"Complete Registration!", 
				"Enter this to change password: "+random+"--->"+user.getEmail());
		
		oper.setOperValidity("Success");
		oper.setDescription("Unique Number sent to user");

		return oper;
	}
	
	
	@PostMapping("/updateProfile")
	public ResponseEntity<?> updateProfile(@ModelAttribute("user") User user, 
			BindingResult bindingResult) {
		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return (ResponseEntity<?>) Constants.operationFailure;
		}

		User userToUpdate = userService.findById(user.getId());
		
		if(user.getAdminManager()!=null)
			userToUpdate.setAdminManager(user.getAdminManager());
		if(user.getIsActive()!=null)
			userToUpdate.setIsActive(user.getIsActive());
		if(user.getIsAdmin()!=null)
			userToUpdate.setIsAdmin(user.getIsAdmin());
		if(user.getIsCandidate()!=null)
			userToUpdate.setIsCandidate(user.getIsCandidate());
		if(user.getIsSuperAdmin()!=null)
			userToUpdate.setIsSuperAdmin(user.getIsSuperAdmin());
		if(user.getEmail()!=null)
			userToUpdate.setEmail(user.getEmail());
		if(user.getgID()!=null)
			userToUpdate.setgID(user.getgID());
		if(user.getName()!=null)
			userToUpdate.setName(user.getName());
		
		
		userService.saveUser(userToUpdate);
		return (ResponseEntity<?>) Constants.operationSuccess;
	}

	@ResponseBody
	@RequestMapping("/getUsers")
	public ResponseEntity<?> getUserByAdminManager(@RequestParam("securityKey")String securityKey){
		
		User user = userService.findUserBySecurityKey(securityKey);
		
		if(user==null)
			return (ResponseEntity<?>) Constants.loginError;
		
		return (ResponseEntity<?>) userService.findUsersByAdmin(user.getEmail());
		
		
	}
	
}
