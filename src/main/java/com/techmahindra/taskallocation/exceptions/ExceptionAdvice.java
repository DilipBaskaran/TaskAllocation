package com.techmahindra.taskallocation.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmahindra.taskallocation.responses.OperationResponse;

//@ControllerAdvice
public class ExceptionAdvice {
	
	
	@ResponseBody
	@ExceptionHandler(value=Exception.class)
	public OperationResponse exceptionHandler(Exception e) {
		OperationResponse oper = new OperationResponse();

		oper.setOperValidity("failure");
		oper.setDescription("Data not accepted! Please retry!");
		oper.setResult(e.getMessage());
		return oper;
	}
}
