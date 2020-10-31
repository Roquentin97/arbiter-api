package com.roquentin.arbiter.models.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConventionsDetailsValidator implements ConstraintValidator<ValidConventionsDetails, String>{
	@Override 
	public boolean isValid(String details, ConstraintValidatorContext ctx) {
		return details.length() >= 5 && details.length() <= 1024;
	}
}
