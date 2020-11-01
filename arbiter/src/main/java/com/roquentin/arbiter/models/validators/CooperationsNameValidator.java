package com.roquentin.arbiter.models.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CooperationsNameValidator implements ConstraintValidator<ValidCooperationsName, String> {
	@Override 
	public boolean isValid(String name, ConstraintValidatorContext ctx) {
		return name.matches("^[\\p{L}|0-9| |`|'|]*$") 
				&& name.length() >= 4 && name.length() <= 50;
	}
}
