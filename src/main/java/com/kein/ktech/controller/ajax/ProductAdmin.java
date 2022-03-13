package com.kein.ktech.controller.ajax;

import com.kein.ktech.domain.Product;
import com.kein.ktech.service.CommentService;
import com.kein.ktech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
public class ProductAdmin {
    @Autowired
    ProductService productService;
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/products/{id}",method = RequestMethod.DELETE)
    public String delProduct(@PathVariable long id){
        Optional<Product> p = productService.findProductById(id);
        if(p.isPresent()){
            Product product = p.get();
            commentService.removeCommentByProduct(product);
        }
        productService.deleteProductById(id);
        return "Delete successfully";
    }
}
