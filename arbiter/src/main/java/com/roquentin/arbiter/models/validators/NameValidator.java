package com.roquentin.arbiter.models.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NameValidator implements ConstraintValidator<NameConstraint, String>{
	
	@Override
	public boolean isValid(String name, ConstraintValidatorContext ctx) {
		return  name != null && 
				name.matches("^[a-z]+[ ][a-z\\s]+$") &&
				name.length() > 3 && name.length() < 50; 
				
	}

}
