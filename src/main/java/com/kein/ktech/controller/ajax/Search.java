package com.kein.ktech.controller.ajax;

import com.kein.ktech.domain.Category;
import com.kein.ktech.domain.Product;
import com.kein.ktech.service.CategoryService;
import com.kein.ktech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class Search {

    @Autowired
    ProductService service;
    @Autowired
    CategoryService categoryService;
    @RequestMapping(value = "/products/{search}" , method = RequestMethod.GET)
    public List<Product> searchProduct(@PathVariable("search") String name)
    {
        List<Product> products  = service.searchProductsByName(name);
        System.out.println("search Term: " + name);
       return service.searchProductsByName(name);
    }
    @RequestMapping(value = "/categories/products/{category}")
    public List<Product> getProductsByCategory(@PathVariable("category") String category)
    {
        System.out.println(category);
        List<Product> products  ;
        Optional<Category> cat =categoryService.getCategoryByName(category);
        if(cat.isPresent()){
            Category st = cat.get();
            System.out.println(st.getId());
            System.out.println(st.getName());
            System.out.println("size"+ st.getProducts().size());
            products = st.getProducts();
            return products;
            //return st.getProducts();
        }
        else{
            return new ArrayList<Product>();
        }

    }

    public ResponseEntity<List<Product>> st()
    {
        var uri = ServletUriComponentsBuilder.fromUriString("abc.xyz");
        List<Product> products = service.getProducts();
        return ResponseEntity.ok(products);
    }
}
