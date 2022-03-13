package com.kein.ktech.validator;

import com.kein.ktech.validator.fileInputValidator.FileInput;
import com.kein.ktech.validator.fileInputValidator.ProductDetailCheck;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { ProductDetailCheck.class })
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductDetailValidator {
    String message() default "Invalid file!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
