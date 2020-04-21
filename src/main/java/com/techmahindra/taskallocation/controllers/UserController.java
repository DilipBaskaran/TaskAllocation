package com.techmahindra.taskallocation.controllers;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@ResponseBody
	@PostMapping("/registration")
	public OperationResponse registration(@Valid @RequestBody User user,
			@RequestHeader(value="securityKey") String securityKey,
			BindingResult bindingResult) {

		//System.out.println(user);

		userValidator.validate(user, bindingResult,true);
		User registeringUser =null;
		if(securityKey==null || securityKey=="") {
			List<User> superAdmins = userService.findSuperAdmins();
			if( superAdmins == null || superAdmins.size() == 0 ) {
				registeringUser = new User();
				registeringUser.setgID("FirstUser");
				registeringUser.setIsSuperAdmin(true);
			}
		}else {
			registeringUser = userService.findUserBySecurityKey(securityKey);
		}

		if (bindingResult.hasErrors() || registeringUser==null || !registeringUser.getIsSuperAdmin())
			return new OperationResponse("failure",
					"User information given are wrong",
					bindingResult.toString());

		user.setPassword(EncodingUtil.encode("abc123"));
		user.setCreatedBy(registeringUser.getgID());
		user.setUpdatedBy(registeringUser.getgID());

		userService.saveUser(user);
		return new OperationResponse("success",
				"User Registration done",
				user);

	}

	@ResponseBody
	@PostMapping("/forgotPassword")
	public OperationResponse forgotPassword(@RequestBody HashMap<String,String> loginMap) {
		String userName = loginMap.get("username");
		if(userName==null)
			return new OperationResponse("failure","Data not correct! User information given are wrong!");
		
		User user = userService.findByUserName(userName);
		if(user == null)
			return new OperationResponse("failure","Data not correct! User information given are wrong!");

		long random = Math.round(Math.random()*10000);
		user.setRandomNo(""+random);
		System.out.println(user+"\nRandom Number: "+random);
		userService.saveUser(user);

		//send Mail to User with unique number
		userService.sendMail(user.getEmail(), "Task Allocation - Forgot Password",
				"Enter this to change password: "+random);
		
		return new OperationResponse("success","Unique Number sent to user");
	}

	@ResponseBody
	@PostMapping("/confirmuser")
	public OperationResponse confirmuser(@RequestBody HashMap<String,String> loginMap) {
		String userName = loginMap.get("username");
		String uniqueno = loginMap.get("uniqueno");		
		if(userName == null || uniqueno == null)
			return new OperationResponse("failure","Data not correct! UserName & Unique Number combination is wrong!");
		
		User user = userService.findByUserName(userName);
		if(user==null || !uniqueno.equalsIgnoreCase(user.getRandomNo())) 
			return new OperationResponse("failure","Data not correct! UserName & Unique Number combination is wrong!");

		return new OperationResponse("success","User Confirmation done");
	}

	@ResponseBody
	@PostMapping("/setPassword")
	public OperationResponse setPassword(@RequestBody HashMap<String,String> loginMap) {
		
		String userName = loginMap.get("username");
		String password = loginMap.get("password");
		if(password == null || userName==null)
			new OperationResponse("failure","Data not correct! UserName Password combination is not accepted");

		User user = userService.findByUserName(userName);
		if (user==null) {
			return new OperationResponse("failure","Data not correct! UserName Password combination is not accepted");
		}

		System.out.println(password);
		System.out.println(EncodingUtil.encode(password.split(",")[0]));
		user.setPassword(EncodingUtil.encode(password.split(",")[0]));
		//user.setIsActive(true);
		userService.saveUser(user);

		return new OperationResponse("success","Password is set properly");
	}


	@ResponseBody
	@GetMapping("/info")
	public Object getUserInfo(@RequestHeader(value="securityKey") String securityKey) {
		User user = userService.findUserBySecurityKey(securityKey);
		if(user==null) 
			return new OperationResponse("failure","Data not correct! Not a valid User!!");

		//System.out.println(user);
		return new OperationResponse("success","API hit with proper details",user);
	}

	@ResponseBody
	@GetMapping("/searchUser")
	public Object searchUser(@RequestHeader(value="securityKey") String securityKey,
			@RequestBody HashMap<String,String> conditionMap) {

		String condition = conditionMap.get("condition");
		if(condition == null)
			return new OperationResponse("failure","Data not correct!","condition param is not available");
		
		User adminUser = userService.findUserBySecurityKey(securityKey);
		if(adminUser==null)			
			return new OperationResponse("failure","Data not correct! Not a valid User!!");

		List<User> users = null;
		users = userService.findByAdminAndNameOrGIdContaining("", condition, condition);
		users.remove(adminUser);

		//System.out.println(users);
		return new OperationResponse("success",
				"Search User results for condition :"+condition,
				users);
	}


	@ResponseBody
	@PostMapping("/updateProfile")
	public OperationResponse updateProfile(@RequestBody User user,
			@RequestHeader(value="securityKey") String securityKey,
			BindingResult bindingResult) {

		User updatingUser = userService.findUserBySecurityKey(securityKey);

		if(updatingUser == null || 
				( updatingUser.getId() != user.getId() && 
				!( updatingUser.getIsAdmin() || updatingUser.getIsSuperAdmin() ) ) ) 
			return new OperationResponse("failure","UpatingUser is not valid!! Please resubmit!!","User can update his profile/ his subordinates");

		userValidator.validate(user, bindingResult,false);

		if (bindingResult.hasErrors() )
			return new OperationResponse("failure","Data is not valid!! Please resubmit!!",bindingResult.toString());

		User userToUpdate = userService.findById(user.getId());
		if(userToUpdate == null)
			return new OperationResponse("failure","Id is not valid!! Please resubmit!!","User to be updated is wrong");

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

		return new OperationResponse("success",
				"User Update Done",
				userToUpdate);
	}
	
	@ResponseBody
	@GetMapping("/getUser/{id}")
	public Object getUser(@RequestHeader(value="securityKey") String securityKey,
			@PathVariable(name="id") Long id){

		User adminUser = userService.findUserBySecurityKey(securityKey);
		if(adminUser==null || !(adminUser.getIsSuperAdmin() || adminUser.getIsAdmin()))
			return new OperationResponse("failure","User Key not correct / User is not valid to hit this api!!");

		User user = userService.findById(id);
		if(user==null)
			return new OperationResponse("failure","User details not present!!","User id is not valid ");


		return new OperationResponse("success",
				"UserDetail for User with id : "+id,
				user);
	}

	@ResponseBody
	@GetMapping("/getUsers")
	public Object getSubordinates(@RequestHeader(value="securityKey") String securityKey){

		User keyUser = userService.findUserBySecurityKey(securityKey);

		if(keyUser==null || !(keyUser.getIsSuperAdmin() || keyUser.getIsAdmin()))
			return new OperationResponse("failure",
					"User Key not correct! User is not valid!!");

		List<User> userList = userService.findAllUsersByAdmin(keyUser);
		//System.out.println(userList);
		return new OperationResponse("success",
				"Users List under the current user:"+keyUser.getId(),
				userList);
	}

	



	@ResponseBody
	@GetMapping("/getUsers/{id}")
	public Object getSubordinateOfOtherUser(@RequestHeader(value="securityKey") String securityKey,
			@PathVariable(name="id") Long id){
		User user = userService.findUserBySecurityKey(securityKey);
		if(user==null || !user.getIsSuperAdmin())
			return new OperationResponse("failure","User Key not correct / User is not valid to hit this api!!");

		User adminUser = userService.findById(id);
		if(adminUser==null || !(adminUser.getIsSuperAdmin() || adminUser.getIsAdmin())) 
			return new OperationResponse("failure","User Key not correct! User is not valid to hit this api!!","User is not Admin/SuperAdmin");

		List<User> users = userService.findAllUsersByAdmin(adminUser);

		return new OperationResponse("success","User List for User with id : "+id,users);
	}

	@ResponseBody
	@DeleteMapping("/deleteUser/{id}")
	public Object deleteUser(@RequestHeader(value="securityKey") String securityKey,
			@PathVariable(name = "id") Long id){
		User superAdminUser = userService.findUserBySecurityKey(securityKey);

		System.out.println(id);
		if(superAdminUser==null || !superAdminUser.getIsSuperAdmin())
			return new OperationResponse("failure","User Key not correct! User is not valid to delete!!");
		
		User userToDelete = userService.findById(id);
		System.out.println(userToDelete);
		if(userToDelete!=null && (userToDelete.getIsAdmin() || userToDelete.getIsSuperAdmin())) {
			List<User> users = userService.findDirectUsersByAdmin(userToDelete.getEmail());
			if(users != null && users.size()!=0)
				return new OperationResponse("failure","User cannot be deleted","Subordinates available under this user");
		}

		userService.deleteUser(userToDelete);
		//System.out.println("userDeleted");

		return new OperationResponse("success","User with id "+id+ " is deleted");
	}

}
