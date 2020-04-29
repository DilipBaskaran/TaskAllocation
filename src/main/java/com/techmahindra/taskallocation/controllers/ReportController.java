package com.techmahindra.taskallocation.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmahindra.taskallocation.exceptions.ResourceNotFoundException;
import com.techmahindra.taskallocation.models.User;
import com.techmahindra.taskallocation.service.TaskService;
import com.techmahindra.taskallocation.service.UserService;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	TaskService taskService;
	
	@Value("${fileupload.folder}")
	private String UPLOADED_FOLDER;
	
	
	@ResponseBody
	@GetMapping("/currentMonth")
	public ResponseEntity<Resource> getCurrentMonthReport(
			@RequestHeader("securitykey") String securityKey, 
			HttpServletRequest request) throws MalformedURLException{
		User superAdminUser = userService.findUserBySecurityKey(securityKey);
		if(superAdminUser==null )
			throw new ResourceNotFoundException("User", "Key", "***ProvidedKey***");
		
		List<String> report = taskService.getCurrentMonthReport();
		
		//System.out.println(report);
		String fileToSave="Report_"+LocalDate.now()+"_"+System.currentTimeMillis()+".csv";
		
		File file = new File(UPLOADED_FOLDER+fileToSave);
		try(FileWriter fileWriter = new FileWriter(file)) {
			
			for(String str : report)
				fileWriter.append(str+"\n");
			
			fileWriter.flush();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new ResourceNotFoundException("File could not be processed", "", "");
		}
		
		Resource resource = new UrlResource(Paths.get(file.getAbsolutePath()).toUri());
        if(!resource.exists()) 
			throw new ResourceNotFoundException("DownloadFIle", "FileName", file.getName());
		
		
		String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}

}
