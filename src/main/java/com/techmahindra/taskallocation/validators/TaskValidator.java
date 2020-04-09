package com.techmahindra.taskallocation.validators;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.service.TaskService;

@Component
public class TaskValidator implements Validator{
	
	@Autowired
    private TaskService taskService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Task.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }

    public void validate(Object o, Errors errors,boolean isCreate) {
        Task task = (Task) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expiry", "NotEmpty");
        
    }
}
