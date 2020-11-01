package com.roquentin.arbiter.models.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = CooperationsDescriptionValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidCooperationsDescription {
	//TODO localize
	String message() default "Description must be at least 5 characters long but not longer than 140";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
