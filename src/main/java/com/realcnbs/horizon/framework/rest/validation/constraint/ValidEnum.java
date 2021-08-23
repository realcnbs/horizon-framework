package com.realcnbs.horizon.framework.rest.validation.constraint;

import com.realcnbs.horizon.framework.rest.validation.validator.ValidEnumValidator;
import com.realcnbs.horizon.framework.rest.validation.validator.ValidEnumsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {ValidEnumValidator.class, ValidEnumsValidator.class})
public @interface ValidEnum {

    Class<? extends Enum<?>> enumClass();

    String message() default "value is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}