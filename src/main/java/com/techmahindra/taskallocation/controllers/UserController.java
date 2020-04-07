package com.techmahindra.taskallocation.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());

		return "register";
	}


	@ResponseBody
	@PostMapping("/registration")
	public OperationResponse registration(@RequestBody User user,
			@RequestParam String securityKey,
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
			oper.setOperValidity("Failure");
			oper.setDescription("User information given are wrong\n"+bindingResult.toString());
			return oper;
		}

		long random = Math.round(Math.random()*10000);

		user.setPassword(EncodingUtil.encode("abc123"));
		user.setRandomNo(""+random);
		user.setCreatedBy(registeringUser.getgID());
		user.setUpdatedBy(registeringUser.getgID());
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

	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "forgotPassword";
	}

	@ResponseBody
	@PostMapping("/forgotPassword")
	public OperationResponse forgotPassword(@RequestParam("username")String username) {

		OperationResponse oper = new OperationResponse();

		oper.setOperValidity("Data not correct");
		oper.setDescription("User information given are wrong");

		User user = userService.findByUserName(username);

		if(user == null)
			return oper;


		long random = Math.round(Math.random()*10000);
		user.setRandomNo(""+random);
		System.out.println(user+"\nRandom Number: "+random);

		userService.saveUser(user);


		userService.sendMail("kumardilip1990b@gmail.com", "Complete Registration!",
				"Enter this to change password: "+random+"--->"+user.getEmail());


		oper.setOperValidity("Success");
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

			oper.setOperValidity("Data not correct");
			oper.setDescription("UserName & OTP combination is wrong");

			return oper;
		}

		OperationResponse oper = new OperationResponse();

		oper.setOperValidity("Success");
		oper.setDescription("User Confirmation done");

		return oper;

	}

	@ResponseBody
	@PostMapping("/setPassword")
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
		System.out.println(EncodingUtil.encode(password.split(",")[0]));
		user.setPassword(EncodingUtil.encode(password.split(",")[0]));
		//user.setIsActive(true);
		userService.saveUser(user);

		oper.setOperValidity("Success");
		oper.setDescription("Password is set properly");

		return oper;
	}


	@ResponseBody
	@GetMapping("/info")
	public Object getUserInfo(@RequestParam("securityKey") String securityKey) {

		User user = userService.findUserBySecurityKey(securityKey);

		if(user==null) {
			OperationResponse oper = new OperationResponse();
			oper.setOperValidity("Data not correct");
			oper.setDescription("Not a valid User!!");
			return oper;
		}

		System.out.println(user);

		return user;

	}


	@ResponseBody
	@PostMapping("/updateProfile")
	public OperationResponse updateProfile(@RequestBody User user,
			@RequestParam String securityKey,
			BindingResult bindingResult) {

		OperationResponse oper = new OperationResponse();
		oper.setOperValidity("Failure! Data not correct!");

		User updatingUser = userService.findUserBySecurityKey(securityKey);

		if(updatingUser == null || 
				( updatingUser.getId() != user.getId() && 
				!( updatingUser.getIsAdmin() || updatingUser.getIsSuperAdmin() ) ) ) {
			oper.setDescription("UpatingUser is not valid!! Please resubmit!!");
			return oper;
		}
		
		userValidator.validate(user, bindingResult,false);

		if (bindingResult.hasErrors() ) {
			oper.setOperValidity("Failure! Data not correct!");
			oper.setDescription("Data is not valid!! Please resubmit!!"+bindingResult.toString()); 
			return oper;
		}


		User userToUpdate = userService.findById(user.getId());
		if(userToUpdate == null) {
			oper.setDescription("Id is not valid!! Please resubmit!!");
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

		oper.setOperValidity("Success");
		oper.setDescription("User Update Done");

		return oper;
	}

	@ResponseBody
	@RequestMapping("/getUsers")
	public Object getUserByAdminManager(@RequestParam("securityKey")String securityKey){

		User user = userService.findUserBySecurityKey(securityKey);

		if(user==null) {
			OperationResponse oper = new OperationResponse();
			oper.setOperValidity("Failure! User Key not correct!");
			oper.setDescription("User is not valid!!");
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

		return copyUsers;


	}

	@ResponseBody
	@ExceptionHandler(value=Exception.class)
	public OperationResponse exceptionHandler() {
		OperationResponse oper = new OperationResponse();

		oper.setOperValidity("Data not correct");
		oper.setDescription("Data not accepted! Please retry!");

		return oper;
	}

}
