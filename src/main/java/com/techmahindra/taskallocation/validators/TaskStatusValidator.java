package com.techmahindra.taskallocation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.techmahindra.taskallocation.models.Task;
import com.techmahindra.taskallocation.models.TaskStatus;
import com.techmahindra.taskallocation.service.TaskStatusService;

@Component
public class TaskStatusValidator implements Validator{
	
	@Autowired
    private TaskStatusService taskStatusService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Task.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }

    public void validate(Object o, Errors errors,boolean isCreate) {
        TaskStatus taskStatus = (TaskStatus) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "statusKey", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "statusName", "NotEmpty");
        
        if(isCreate && taskStatusService.findByStatusKey(taskStatus.getStatusKey())!=null)
        	errors.rejectValue("statusKey", "statusKey should be unique");
        	
        
    }
}
