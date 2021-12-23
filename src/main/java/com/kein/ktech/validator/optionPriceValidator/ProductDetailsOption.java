package com.kein.ktech.validator.optionPriceValidator;

import com.kein.ktech.domain.OptionProductDetails;
import com.kein.ktech.domain.ProductDetails;
import com.kein.ktech.validator.FileInputValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ProductDetailsOption implements ConstraintValidator<FileInputValidator, ProductDetails> {
    @Override
    public boolean isValid(ProductDetails value, ConstraintValidatorContext context) {
        List<OptionProductDetails> ls = value.getOptionProductDetails();
        for(OptionProductDetails op : ls){
            if(op.getPriceOption() <1) return false;
        }
        return true;
    }
}
