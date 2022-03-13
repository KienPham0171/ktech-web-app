package com.kein.ktech.validator.fileInputValidator;

import com.kein.ktech.validator.FileInputValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileInput implements ConstraintValidator<FileInputValidator, MultipartFile> {
    @Override
    public void initialize(FileInputValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if(value == null || value.getSize() ==0 ){
            return false;
        }else return true;
    }

}
