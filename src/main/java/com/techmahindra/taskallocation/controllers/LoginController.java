package com.techmahindra.taskallocation.controllers;

import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmahindra.taskallocation.models.User;
import com.techmahindra.taskallocation.responses.OperationResponse;
import com.techmahindra.taskallocation.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	/*
	 * @RequestMapping(value = {"/","/index","/home"}) public String index(Model
	 * model){ String userName=null;//getCurrentUserName();
	 * 
	 * 
	 * 
	 * //System.out.println(userName); if(userName == null) return "login";
	 * if(userName != null) { User user = userService.findByUserName(userName);
	 * //System.out.println(user.getIsActive()); if(user.getIsActive()==false) {
	 * model.addAttribute("username", userName); return "confirm"; } }
	 * 
	 * model.addAttribute("userName",userName); return "home"; }
	 */



	/*
	 * @RequestMapping("/login") public String login(Model model){ return "login"; }
	 */

	@ResponseBody
	@PostMapping("/login")
	public OperationResponse loginReq(@RequestParam("username") String userName,
			@RequestParam("password") String password){


		User user = userService.findByUserName(userName);

		System.out.println(userName);
		System.out.println(password);
		OperationResponse oper = new OperationResponse();


		if (user != null  
				&& user.getPassword().equals(
						Base64.getEncoder().encodeToString(password.getBytes())) ) {
			
			oper.setOperValidity("success");
			
						
			String securityKey = Base64.getEncoder() 
					.encodeToString(
							(userName+":"+password+":"+LocalDateTime.now().toString()).getBytes());
			oper.setDescription("Login is successful!!");
			oper.setResult(securityKey);
			user.setSecurityKey(securityKey);
			userService.saveUser(user);
		}else {
			oper.setOperValidity("failure");
			oper.setDescription("UserName Password combination is not accepted");
		}
		
		return oper;
	}

}
