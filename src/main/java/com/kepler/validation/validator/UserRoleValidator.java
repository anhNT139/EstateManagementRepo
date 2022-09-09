package com.kepler.validation.validator;

import com.kepler.validation.annotation.UserRoleConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class UserRoleValidator implements ConstraintValidator<UserRoleConstraint, String> {
    private static final List<String> roles = Arrays.asList("manager", "staff");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return roles.contains(value);
    }
}
