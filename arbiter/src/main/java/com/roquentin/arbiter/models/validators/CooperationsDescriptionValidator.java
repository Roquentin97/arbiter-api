package com.roquentin.arbiter.models.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CooperationsDescriptionValidator implements ConstraintValidator<ValidCooperationsDescription, String> {
	
	@Override
	public boolean isValid(String description, ConstraintValidatorContext ctx) {
		return description.length() >= 5 && description.length() <= 140;
	}

}
