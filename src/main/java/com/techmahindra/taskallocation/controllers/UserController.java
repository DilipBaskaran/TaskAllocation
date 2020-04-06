package com.techmahindra.taskallocation.controllers;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmahindra.taskallocation.models.User;
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

	@PostMapping("/confirmuser")
	public String confirmuser(@RequestParam("username") String userName,
			@RequestParam("uniqueno") String uniqueno, 
			Model model) {

		User user = userService.findByUserName(userName);
		//System.out.println(user);

		if(user==null)
			return "redirect:/login";

		if(!uniqueno.equalsIgnoreCase(user.getRandomNo()))
			return "confirm";

		model.addAttribute("username", userName);
		return "ChangePassword";

	}

	@PostMapping("/registration")
	public String registration(@ModelAttribute("user") User user, 
			BindingResult bindingResult) {
		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return "register";
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

		return "redirect:/home";
	}

	@PostMapping("/setpassword")
	public String setPassword(@RequestParam("username") String userName,
			@RequestParam("password") String password) {

		if(password == null || userName==null)
			return "redirect:/login";

		User user = userService.findByUserName(userName);

		if (user==null) {
			return "redirect:/login";
		}
		
		System.out.println(password);
		System.out.println(Base64.getEncoder() 
                .encodeToString( password.split(",")[0].getBytes()));
		user.setPassword(Base64.getEncoder() 
                .encodeToString( password.getBytes()));
		user.setIsActive(true);
		userService.saveUser(user);

		return "redirect:/home";
	}

	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "forgotPassword";
	}

	@PostMapping("/forgotPassword")
	public String forgotPassword(@ModelAttribute("username")String username,
			BindingResult error,
			Model model) {

		User user = userService.findByUserName(username);

		if(user == null)
			return "forgotPassword";

		model.addAttribute("username", username);
		
		long random = Math.round(Math.random()*10000);
		user.setRandomNo(""+random);
		user.setIsActive(false);
		System.out.println(user+"\nRandom Number: "+random);
		
		userService.saveUser(user);

		userService.sendMail("kumardilip1990b@gmail.com",
				"Complete Registration!", 
				"Enter this to change password: "+random+"--->"+user.getEmail());
		


		return "confirm";
	}
	
	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute("user") User user, 
			BindingResult bindingResult) {
		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return "register";
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
		
		/*
		 * userService.sendMail("kumardilip1990b@gmail.com", "Complete Registration!",
		 * "Enter this to change password: "+random+"--->"+user.getEmail());
		 */
		return "redirect:/home";
	}

	@ResponseBody
	@RequestMapping("/getUsers")
	public List<User> getUserByAdminManager(@RequestParam("securityKey")){
		
	}
	
}
