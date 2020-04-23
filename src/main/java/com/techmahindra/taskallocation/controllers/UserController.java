package com.techmahindra.taskallocation.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.techmahindra.taskallocation.exceptions.ResourceNotFoundException;
import com.techmahindra.taskallocation.models.User;
import com.techmahindra.taskallocation.responses.OperationResponse;
import com.techmahindra.taskallocation.service.UserService;
import com.techmahindra.taskallocation.util.EncodingUtil;
import com.techmahindra.taskallocation.validators.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private final String headerCSV = "GID,Name,Email,MobileNo,AdminManager,isActive,isSuperAdmin,isAdmin,isCandidate";  

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Value("${fileupload.folder}")
	private String UPLOADED_FOLDER;

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

	@ResponseBody
	@PostMapping("/uploadUsers")
	public OperationResponse uploadUsersExcel(
			@RequestParam("usersFile") MultipartFile usersFile,
			@RequestHeader("securityKey") String securityKey
			) throws IOException {
		User superAdminUser = userService.findUserBySecurityKey(securityKey);

		if(superAdminUser==null )
			return new OperationResponse("failure","User Key not correct! User is not valid to upload!!");

		String uploadedFileName = usersFile.getOriginalFilename();
		if (StringUtils.isEmpty(uploadedFileName) || 
				!(uploadedFileName.endsWith("xls")
						|| uploadedFileName.endsWith("xlsx") 
						|| uploadedFileName.endsWith("csv"))) 
			return new OperationResponse("failure","File is not valid",
					"File should be in the CSV Format");

		Path savedFile = saveUploadedFile(usersFile);
		List<User> userList = new LinkedList<User>();
		List<String> savedUserList = new LinkedList<String>();
		List<String> unSavedUserList = new LinkedList<String>();
		String userSavedvsUnSaved="";
		try (Stream<String> stream = Files.lines(savedFile)){
			stream.forEach(str->{
				String[] user = str.split(",");
				if(!user[0].equals("GID")) {
					User userObj = new User(user[1].trim(),
							user[0].trim(),
							user[2].trim(),
							user[4].trim().equals("")?null:user[4].trim(),
							user[5].trim().equalsIgnoreCase("YES"),
							user[6].trim().equalsIgnoreCase("YES"),
							user[7].trim().equalsIgnoreCase("YES"),
							user[8].trim().equalsIgnoreCase("YES"));
					
					userObj.setPassword(EncodingUtil.encode("abc123"));
					userObj.setCreatedBy(superAdminUser.getgID());
					userObj.setUpdatedBy(superAdminUser.getgID());
					
					userList.add(userObj);
					try {
						userService.saveUser(userObj);
						savedUserList.add(str);
					}catch(Exception ex) {
						unSavedUserList.add(str);
					}
				}
			});
		}
		if(unSavedUserList.size()!=0) {
			userSavedvsUnSaved+="UnSaved User List";
			userSavedvsUnSaved+="\n"+headerCSV;
			for(String str : unSavedUserList)
				userSavedvsUnSaved+="\n"+str;	
		}
		if(savedUserList.size()!=0) {
			userSavedvsUnSaved+="\nSaved User List";
			userSavedvsUnSaved+="\n"+headerCSV;
			for(String str : savedUserList) 
				userSavedvsUnSaved+="\n"+str;
		}
		
		String fileToSave="UserSaved-UnSaved_"+System.currentTimeMillis()+".csv";
		
		try(FileWriter fileWriter = new FileWriter(UPLOADED_FOLDER+fileToSave)) {
			fileWriter.write(userSavedvsUnSaved);
		} catch (Exception e) {
			//e.printStackTrace();
			return new OperationResponse("failure","File is not valid",
					"Re-upload with proper value");
		}
		
		return new OperationResponse("success","File Uploaded","/user/download/"+fileToSave);
	}
	
	@ResponseBody
	@GetMapping("/download/{downloadFile}")
	public ResponseEntity<Resource> downloadFile(
			//@RequestHeader("securitykey") String securityKey,
			@PathVariable("downloadFile") String downloadFile, HttpServletRequest request) throws MalformedURLException{
		/*
		 * User superAdminUser = userService.findUserBySecurityKey(securityKey);
		 * 
		 * if(superAdminUser==null ) throw new ResourceNotFoundException("User",
		 * "securityKey", "MentionedValue");
		 */
		
		Path filePath=  Paths.get(UPLOADED_FOLDER).toAbsolutePath().normalize();
		
		filePath = filePath.resolve(downloadFile).normalize();
		Resource resource = new UrlResource(filePath.toUri());
        if(!resource.exists()) 
			throw new ResourceNotFoundException("DownloadFIle", "FileName", downloadFile);
		
		
		String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        
        System.out.println("Resource Generated...");
        
        
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	

	private Path saveUploadedFile(MultipartFile file) throws IOException{
		byte[] bytes = file.getBytes();
		String name = file.getOriginalFilename();
		Path path = Paths.get(UPLOADED_FOLDER + name);
		Files.write(path, bytes);
		return path;

	}
}
