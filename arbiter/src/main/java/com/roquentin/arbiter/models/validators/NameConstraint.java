package com.roquentin.arbiter.models.validators;
import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = NameValidator.class )
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameConstraint {
	String message() default "Invalid user name";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
