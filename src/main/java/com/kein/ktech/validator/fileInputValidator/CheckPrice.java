package com.kein.ktech.validator.fileInputValidator;

import com.kein.ktech.domain.OptionProductDetails;
import com.kein.ktech.domain.ProductDetails;
import com.kein.ktech.domain.SmallImagesProduct;
import com.kein.ktech.validator.ProductDetailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CheckPrice implements ConstraintValidator<ProductDetailValidator, ProductDetails> {
    @Override
    public void initialize(ProductDetailValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ProductDetails value, ConstraintValidatorContext context) {
        List<OptionProductDetails> checkList = value.getOptionProductDetails();
        boolean check = true;
        for(OptionProductDetails imgObj : checkList){
            if(imgObj.getPriceOption() <= 1 || imgObj.getPriceOption() >= 10000)
                check = false;
        }
        return check;
    }
}
