package com.roquentin.arbiter.models.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext ctx) {
		return 
				username != null &&
				username.matches("^[a-zA-Z0-9]+$") &&
				username.length() > 5 && username.length() < 20;
	}
}



