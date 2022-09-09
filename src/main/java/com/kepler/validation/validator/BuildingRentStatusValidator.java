package com.kepler.validation.validator;

import com.kepler.enums.BuildingRentStatusEnum;
import com.kepler.validation.annotation.BuildingRentStatusConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BuildingRentStatusValidator implements ConstraintValidator<BuildingRentStatusConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            BuildingRentStatusEnum.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
