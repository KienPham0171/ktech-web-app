package com.kein.ktech.validator;

import com.kein.ktech.validator.fileInputValidator.ProductDetailsImage;
import com.kein.ktech.validator.optionPriceValidator.ProductDetailsOption;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {
        ProductDetailsOption.class })
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionPrice {
    String message() default "Prices is not valid (>1) !";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
