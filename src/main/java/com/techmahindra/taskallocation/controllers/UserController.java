package com.techmahindra.taskallocation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


	private String getCurrentUserName(){
		String currentUserName = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
		}
		return currentUserName;
	}

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());

		return "register";
	}

	@PostMapping("/confirmuser")
	public String confirmuser(@ModelAttribute("uniqueno") String uniqueno, 
			BindingResult bindingResult,Model model) {

		String userName = getCurrentUserName();
		//System.out.println(userName);
		User user = userService.findByUserName(userName);
		//System.out.println(user);

		if(user==null)
			return "redirect:/login";

		if(!uniqueno.equalsIgnoreCase(user.getRandomNo()))
			bindingResult.rejectValue("uniqueno", "Enter correct uniqueno");

		if (bindingResult.hasErrors()) {
			return "confirm";
		}

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
	public String setPassword(@ModelAttribute("password") String password) {

		if(password == null)
			return "redirect:/login";

		User user = userService.findByUserName(getCurrentUserName());

		if (user==null) {
			return "redirect:/login";
		}

		user.setPassword(password);
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



		return "redirect:/home";
	}

}
