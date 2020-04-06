package com.techmahindra.taskallocation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmahindra.taskallocation.models.User;
import com.techmahindra.taskallocation.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	private String getCurrentUserName(){
		String currentUserName = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
		}
		return currentUserName;
	}

	@ResponseBody
	@RequestMapping(value = {"/user/json"})
	public User userJSON(Model model){		
		String userName=getCurrentUserName();
		//System.out.println(userName);
		User user = null;
		if(userName == null)
			return user;
		if(userName != null) {
			user = userService.findByUserName(userName);
			//System.out.println(user.getIsActive());
			if(user.getIsActive()==false) {
				model.addAttribute("username", userName);
				return user;
			}
		}
		
		model.addAttribute("userName",userName);
		return user;
	}
	
	@RequestMapping(value = {"/","/index","/home"})
	public String index(Model model){		
		String userName=getCurrentUserName();
		//System.out.println(userName);
		if(userName == null)
			return "login";
		if(userName != null) {
			User user = userService.findByUserName(userName);
			//System.out.println(user.getIsActive());
			if(user.getIsActive()==false) {
				model.addAttribute("username", userName);
				return "confirm";
			}
		}
		
		model.addAttribute("userName",userName);
		return "home";
	}
	
	

	@RequestMapping("/login")
	public String login(Model model){
		String userName=getCurrentUserName();
		if(userName != null)
			return "redirect:/";
		return "login";
	}

}
