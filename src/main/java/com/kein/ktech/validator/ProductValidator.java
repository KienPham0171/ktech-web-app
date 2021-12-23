package com.kein.ktech.validator;

import com.kein.ktech.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product p = (Product) target;
        MultipartFile file = (MultipartFile) p.getFile();
        if(file == null){
            errors.rejectValue("image","Can not be null");
        }

    }
}
