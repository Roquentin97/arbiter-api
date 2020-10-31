package com.roquentin.arbiter.models.validators;
import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UsersNameValidator.class )
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsersName {
	// TODO localize
	String message() default "Invalid user name. Names must include only letters. Total characters number must not exceed 50. You can give up to 3 names separated with whitespaces.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
