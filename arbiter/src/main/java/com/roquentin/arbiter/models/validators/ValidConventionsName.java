package com.roquentin.arbiter.models.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConventionsNameValidator.class)
@Documented
public @interface ValidConventionsName {
	//TODO localize
	String message() default "Must include only letters, digits, whitespaces and apostrofs. Appropriate length is 4-50 characters";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
