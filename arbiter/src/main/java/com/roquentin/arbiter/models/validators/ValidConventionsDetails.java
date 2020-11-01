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
@Constraint(validatedBy = ConventionsDetailsValidator.class)
@Documented
public @interface ValidConventionsDetails {
	// TODO localize
	String message() default "The field may contain 5-1024 characters";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
