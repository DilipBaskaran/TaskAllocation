package com.techmahindra.taskallocation.config;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	public static final Map<String,String> loginError = new HashMap<String,String>();
	
	static {
		loginError.put("login", "failure");
	}
	
	public static final Map<String,String> loginSuccess = new HashMap<String,String>();
	
	static {
		loginError.put("login", "success");
	}
	
	public static final Map<String,String> operationSuccess = new HashMap<String,String>();
	
	static {
		loginError.put("login", "failure");
	}
	
public static final Map<String,String> operationFailure= new HashMap<String,String>();
	
	static {
		loginError.put("operation", "failure");
	}

	
}
