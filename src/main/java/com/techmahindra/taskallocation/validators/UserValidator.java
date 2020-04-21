package com.techmahindra.taskallocation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.techmahindra.taskallocation.models.User;
import com.techmahindra.taskallocation.service.UserService;

@Component
public class UserValidator implements Validator{
	
	@Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }

    public void validate(Object o, Errors errors,boolean isCreate) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gID", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isActive", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isSuperAdmin", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isAdmin", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isCandidate", "NotEmpty");
        
        if(!isCreate && user.getId() == null)
        	errors.rejectValue("id", "User id should not be empty while updating");
        
        if (user.getgID().length() != 10)
            errors.rejectValue("gID", "Size.user.username");
        
  
        if(isCreate && userService.findByUserName(user.getgID())!=null)
        	errors.rejectValue("gID", "UserName is already present");
        
        if(isCreate && userService.findByEmail(user.getEmail())!=null)
        	errors.rejectValue("gID", "User Email is already present");
        
        if(user.getAdminManager() != null && user.getAdminManager() != "" ) {
        	
        	User admin = userService.findByEmail(user.getAdminManager());
        	if(admin == null || !(admin.getIsAdmin() || admin.getIsSuperAdmin()))
        		errors.rejectValue("adminManager", "Admin Manager is not admin/superadmin");
        }

    }
}
