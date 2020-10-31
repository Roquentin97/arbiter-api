package com.roquentin.arbiter.models.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
	implements ConstraintValidator<PasswordMatches, Object> {
	
	@Override
	public void initialize(PasswordMatches constraintAnnotation) {}
	
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		// TODO implement
		return false;
	}
}
