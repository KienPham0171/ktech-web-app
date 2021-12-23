package com.kein.ktech.validator;

import com.kein.ktech.validator.fileInputValidator.FileInput;
import com.kein.ktech.validator.fileInputValidator.ProductDetailsImage;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { FileInput.class,
        ProductDetailsImage.class })
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileInputValidator {
    String message() default "Invalid file!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
