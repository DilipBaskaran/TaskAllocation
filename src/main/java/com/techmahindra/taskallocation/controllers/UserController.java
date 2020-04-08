package com.techmahindra.taskallocation.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmahindra.taskallocation.models.User;
import com.techmahindra.taskallocation.responses.OperationResponse;
import com.techmahindra.taskallocation.service.UserService;
import com.techmahindra.taskallocation.util.EncodingUtil;
import com.techmahindra.taskallocation.validators.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	/*
	 * @GetMapping("/registration") public String registration(Model model) {
	 * model.addAttribute("user", new User());
	 * 
	 * return "register"; }
	 */


	@ResponseBody
	@PostMapping("/registration")
	public OperationResponse registration(@Valid @RequestBody User user,
			@RequestHeader(value="securityKey") String securityKey,
			BindingResult bindingResult) {

		System.out.println(user);

		userValidator.validate(user, bindingResult,true);
		User registeringUser =null;
		if(securityKey==null || securityKey=="") {
			if(userService.findSuperAdmins().size() ==0) {
				registeringUser = new User();
				registeringUser.setgID("FirstUser");
			}
		}else {
			registeringUser = userService.findUserBySecurityKey(securityKey);
		}

		if (bindingResult.hasErrors() || registeringUser==null || !registeringUser.getIsSuperAdmin()) {
			OperationResponse oper = new OperationResponse();
			oper.setOperValidity("failure");
			oper.setDescription("User information given are wrong");
			oper.setResult(bindingResult.toString());
			return oper;
		}

		user.setPassword(EncodingUtil.encode("abc123"));
		user.setCreatedBy(registeringUser.getgID());
		user.setUpdatedBy(registeringUser.getgID());

		userService.saveUser(user);

		OperationResponse oper = new OperationResponse();

		oper.setOperValidity("success");
		oper.setDescription("User Registration done");
		oper.setResult(user);

		return oper;
	}

	/*
	 * @GetMapping("/forgotPassword") public String forgotPassword() { return
	 * "forgotPassword"; }
	 */

	@ResponseBody
	@PostMapping("/forgotPassword")
	public OperationResponse forgotPassword(@RequestParam("username") String username) {

		OperationResponse oper = new OperationResponse();

		oper.setOperValidity("failure");
		oper.setDescription("Data not correct! User information given are wrong!");

		User user = userService.findByUserName(username);

		if(user == null)
			return oper;


		long random = Math.round(Math.random()*10000);
		user.setRandomNo(""+random);
		
		System.out.println(user+"\nRandom Number: "+random);

		userService.saveUser(user);


		userService.sendMail(user.getEmail(), "Complete Registration!",
				"Enter this to change password: "+random+"--->"+user.getEmail());


		oper.setOperValidity("success");
		oper.setDescription("Unique Number sent to user");

		return oper;
	}

	@ResponseBody
	@PostMapping("/confirmuser")
	public OperationResponse confirmuser(@RequestParam("username") String userName,
			@RequestParam("uniqueno") String uniqueno) {

		User user = userService.findByUserName(userName);
		//System.out.println(user);

		if(user==null || !uniqueno.equalsIgnoreCase(user.getRandomNo())) {

			OperationResponse oper = new OperationResponse();

			oper.setOperValidity("failure");
			oper.setDescription("Data not correct! UserName & Unique Number combination is wrong!");

			return oper;
		}

		OperationResponse oper = new OperationResponse();

		oper.setOperValidity("success");
		oper.setDescription("User Confirmation done");

		return oper;

	}

	@ResponseBody
	@PostMapping("/setPassword")
	public OperationResponse setPassword(@RequestHeader(value="securityKey") String userName,
			@RequestParam("password") String password) {

		OperationResponse oper = new OperationResponse();

		oper.setOperValidity("failure");
		oper.setDescription("Data not correct! UserName Password combination is not accepted");


		if(password == null || userName==null)
			return oper;

		User user = userService.findByUserName(userName);

		if (user==null) {
			return oper;
		}

		System.out.println(password);
		System.out.println(EncodingUtil.encode(password.split(",")[0]));
		user.setPassword(EncodingUtil.encode(password.split(",")[0]));
		//user.setIsActive(true);
		userService.saveUser(user);

		oper.setOperValidity("success");
		oper.setDescription("Password is set properly");

		return oper;
	}


	@ResponseBody
	@GetMapping("/info")
	public Object getUserInfo(@RequestHeader(value="securityKey") String securityKey) {

		User user = userService.findUserBySecurityKey(securityKey);
		OperationResponse oper = new OperationResponse();
		if(user==null) {
			
			oper.setOperValidity("failure");
			oper.setDescription("Data not correct! Not a valid User!!");
			return oper;
		}

		System.out.println(user);
		
		
		oper.setOperValidity("success");
		oper.setDescription("API hit with proper details");
		oper.setResult(user);
		
		return oper;

	}


	@ResponseBody
	@PostMapping("/updateProfile")
	public OperationResponse updateProfile(@RequestBody User user,
			@RequestHeader(value="securityKey") String securityKey,
			BindingResult bindingResult) {

		OperationResponse oper = new OperationResponse();
		oper.setOperValidity("failure");

		User updatingUser = userService.findUserBySecurityKey(securityKey);

		if(updatingUser == null || 
				( updatingUser.getId() != user.getId() && 
				!( updatingUser.getIsAdmin() || updatingUser.getIsSuperAdmin() ) ) ) {
			oper.setDescription("UpatingUser is not valid!! Please resubmit!!");
			oper.setResult("User can update his profile/ his subordinates");
			return oper;
		}
		
		userValidator.validate(user, bindingResult,false);

		if (bindingResult.hasErrors() ) {
			oper.setOperValidity("failure");
			oper.setDescription("Data is not valid!! Please resubmit!!");
			oper.setResult(bindingResult.toString());
			return oper;
		}


		User userToUpdate = userService.findById(user.getId());
		if(userToUpdate == null) {
			oper.setDescription("Id is not valid!! Please resubmit!!");
			oper.setResult("User to be updated is wrong");
			return oper;
		}

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

		userToUpdate.setUpdatedBy(updatingUser.getgID());

		userService.saveUser(userToUpdate);

		oper.setOperValidity("success");
		oper.setDescription("User Update Done");
		oper.setResult(userToUpdate);

		return oper;
	}

	@ResponseBody
	@GetMapping("/getUsers")
	public Object getUserByAdminManager(@RequestHeader(value="securityKey") String securityKey){

		User user = userService.findUserBySecurityKey(securityKey);

		OperationResponse oper = new OperationResponse();
		if(user==null) {
			oper.setOperValidity("failure");
			oper.setDescription("User Key not correct! User is not valid!!");
			return oper;
		}

		List<User> users = userService.findUsersByAdmin(user.getEmail());

		List<User> copyUsers  = new ArrayList(users);

		for(User userRec : users) {
			if(userRec.getIsAdmin()==true || userRec.getIsSuperAdmin()==true) {
				List<User> subordinates = userService.findUsersByAdmin(userRec.getEmail()); 
				if(subordinates != null)
					copyUsers.addAll(subordinates);
			}
		}

		System.out.println(copyUsers);

		oper.setOperValidity("success");
		oper.setDescription("API is hit with proper input");
		oper.setResult(copyUsers);
		return oper;


	}

	@ResponseBody
	@ExceptionHandler(value=Exception.class)
	public OperationResponse exceptionHandler(Exception e) {
		OperationResponse oper = new OperationResponse();

		oper.setOperValidity("failure");
		oper.setDescription("Data not accepted! Please retry!");
		oper.setResult(e.getMessage());
		return oper;
	}

}
