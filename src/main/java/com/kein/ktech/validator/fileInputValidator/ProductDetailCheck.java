package com.kein.ktech.validator.fileInputValidator;

import com.kein.ktech.domain.ProductDetails;
import com.kein.ktech.domain.SmallImagesProduct;
import com.kein.ktech.validator.FileInputValidator;
import com.kein.ktech.validator.ProductDetailValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ProductDetailCheck implements ConstraintValidator<ProductDetailValidator, ProductDetails> {
    @Override
    public void initialize(ProductDetailValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ProductDetails value, ConstraintValidatorContext context) {
        List<SmallImagesProduct> checkList = value.getSmallImagesProducts();
        boolean check = true;
        for(SmallImagesProduct imgObj : checkList){
            if( imgObj.getMultipartFile() == null ||imgObj.getMultipartFile().getSize() == 0   )
                //imgObj.getMultipartFile().getSize() == 0 ||
                check = false;
        }
        return check;
    }
}
