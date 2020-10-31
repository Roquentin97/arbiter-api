package com.roquentin.arbiter.models.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {
	String message() default "Invalid username. Username can must include only letters and numbers. Possible length of the username is 5-25 characters";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {}; 
}
