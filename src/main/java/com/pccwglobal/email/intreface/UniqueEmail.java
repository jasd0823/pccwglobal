package com.pccwglobal.email.intreface;

import com.pccwglobal.email.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
@Documented
public @interface UniqueEmail {
    String message() default "Email address is already registered";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
