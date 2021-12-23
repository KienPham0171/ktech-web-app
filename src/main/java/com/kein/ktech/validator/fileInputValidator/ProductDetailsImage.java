package com.kein.ktech.validator.fileInputValidator;

import com.kein.ktech.domain.SmallImagesProduct;
import com.kein.ktech.validator.FileInputValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ProductDetailsImage implements ConstraintValidator<FileInputValidator, com.kein.ktech.domain.ProductDetails> {


    @Override
    public boolean isValid(com.kein.ktech.domain.ProductDetails value, ConstraintValidatorContext context) {
        List<SmallImagesProduct> ls = value.getSmallImagesProducts();
        for(SmallImagesProduct s :ls){
            if (s.getMultipartFile().getSize() == 0)
                return false;
        }
        return true;

    }
}
