package com.realcnbs.horizon.framework.rest.validation.validator;

import com.google.common.base.Joiner;
import com.realcnbs.horizon.framework.rest.validation.constraint.ValidEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ValidEnumsValidator extends AbstractValidator implements ConstraintValidator<ValidEnum, List<String>> {

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
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        for (String s : value) {
            if (!valueList.contains(s)) {
                setMessage("Valid values are: [" + Joiner.on(", ").join(valueList) + "]", context);
                return false;
            }
        }
        return true;
    }
}