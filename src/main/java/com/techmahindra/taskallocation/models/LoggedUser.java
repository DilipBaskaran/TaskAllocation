package com.techmahindra.taskallocation.models;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoggedUser
implements ValueGenerator<String> {

	private String getCurrentUserName(){
		String currentUserName = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
		}
		return currentUserName;
	}

	@Override
	public String generateValue(
			Session session, Object owner) {
		return getCurrentUserName();
	}
}
