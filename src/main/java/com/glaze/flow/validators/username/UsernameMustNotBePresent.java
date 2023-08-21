package com.glaze.flow.validators.username;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
<<<<<<< HEAD
=======

>>>>>>> 45406c528a39c17117586fc7ca348bac9b05a35e
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameConstraintValidator.class)
public @interface UsernameMustNotBePresent {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
