package com.realcnbs.framework.rest.validation.validator;

import com.google.common.base.Joiner;
import com.realcnbs.framework.rest.validation.constraint.ValidEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ValidEnumValidator extends AbstractValidator implements ConstraintValidator<ValidEnum, String> {

    List<String> valueList = null;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        valueList = new ArrayList<>();
        Class<? extends Enum> enumClass = constraintAnnotation.enumClass();
        Enum[] enumValArr = enumClass.getEnumConstants();
        for (Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        setMessage("Valid values are: [" + Joiner.on(", ").join(valueList) + "]", context);
        return valueList.contains(value);
    }
}