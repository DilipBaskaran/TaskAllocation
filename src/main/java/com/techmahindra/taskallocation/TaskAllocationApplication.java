package com.techmahindra.taskallocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication
public class TaskAllocationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskAllocationApplication.class, args);
	}
	
}
