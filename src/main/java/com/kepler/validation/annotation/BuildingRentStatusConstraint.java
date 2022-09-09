package com.kepler.validation.annotation;


import com.kepler.validation.validator.BuildingRentStatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BuildingRentStatusValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BuildingRentStatusConstraint {

    String message() default "Trạng thái tòa nhà không hợp lệ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
