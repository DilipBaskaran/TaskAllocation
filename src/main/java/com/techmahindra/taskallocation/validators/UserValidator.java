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
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gID", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isSuperAdmin", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isAdmin", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isCandidate", "NotEmpty");
        
        if (user.getgID().length() != 10) {
            errors.rejectValue("gID", "Size.user.username");
        }
        if (userService.findByUserName(user.getUserName()) != null) {
            errors.rejectValue("gID", "Duplicate.user.username");
        }
        if (user.getUserName().length() != 10) {
            errors.rejectValue("gID", "Size.user.username");
        }

    }

}
