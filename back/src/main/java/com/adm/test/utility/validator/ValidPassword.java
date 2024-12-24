package com.adm.test.utility.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PwdCustomValidator.class)
public @interface ValidPassword {

    String message() default "Password does not meet the required policy.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
