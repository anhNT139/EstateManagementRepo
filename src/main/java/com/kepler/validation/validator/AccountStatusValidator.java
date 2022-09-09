package com.kepler.validation.validator;

import com.kepler.enums.UserStatusEnum;
import com.kepler.validation.annotation.AccountStatusConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountStatusValidator implements ConstraintValidator<AccountStatusConstraint, Integer> {

    @Override
    public boolean isValid(Integer statusCode, ConstraintValidatorContext constraintValidatorContext) {
        for (UserStatusEnum item: UserStatusEnum.values()) {
            if (item.getStatusCode().equals(statusCode)) {
                return true;
            }
        }
        return false;
    }

}
