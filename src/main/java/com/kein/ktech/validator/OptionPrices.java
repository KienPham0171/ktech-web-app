package com.kein.ktech.validator;

import com.kein.ktech.validator.fileInputValidator.CheckPrice;
import com.kein.ktech.validator.fileInputValidator.ProductDetailCheck;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { CheckPrice.class })
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionPrices {
    String message() default "Invalid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
