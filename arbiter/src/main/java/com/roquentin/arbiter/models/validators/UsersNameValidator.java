package com.roquentin.arbiter.models.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UsersNameValidator implements ConstraintValidator<ValidUsersName, String>{
	
	@Override
	public boolean isValid(String name, ConstraintValidatorContext ctx) {
		return  name != null && 
				name.matches("^([\\p{L}]*[ ]?){1,3}$") &&
				name.length() >= 3 && name.length() <= 50; 
				
	}

}
