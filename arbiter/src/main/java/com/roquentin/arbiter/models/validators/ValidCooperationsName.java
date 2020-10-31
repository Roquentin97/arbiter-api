package com.roquentin.arbiter.models.validators;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CooperationsNameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidCooperationsName {
	//TODO localize
	String message() default "Invalid cooperations name. Can contain only letters, digits, apostrofs and whitespaces. "
			+ "	Appropriate length is 4-50 characters. ";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
