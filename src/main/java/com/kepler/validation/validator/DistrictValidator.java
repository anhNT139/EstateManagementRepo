package com.kepler.validation.validator;

import com.kepler.enums.DistrictsEnum;
import com.kepler.validation.annotation.DistrictConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DistrictValidator implements ConstraintValidator<DistrictConstraint, String> {

    @Override
    public boolean isValid(String districtCode, ConstraintValidatorContext constraintValidatorContext) {
        try {
            DistrictsEnum.valueOf(districtCode);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
