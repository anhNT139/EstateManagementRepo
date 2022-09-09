package com.kepler.validation.validator;

import com.kepler.enums.BuildingTypesEnum;
import com.kepler.validation.annotation.BuildingTypeConstraint;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class BuildingTypeValidator implements ConstraintValidator<BuildingTypeConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (StringUtils.isBlank(value)) return true;

        try {
            String[] types = value.split(",");
            for (String type: types) {
                BuildingTypesEnum.valueOf(type);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
