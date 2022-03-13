package com.kein.ktech.service;

import com.kein.ktech.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts();
    List<Product> getProductsByPageAndSize(int page, int size);
    List<Product> searchProductsByName(String name);
    Optional<Product> findProductById(long id);
    void saveProduct(Product product);
    long countProducts();
    Product updateProduct(Product product);

    void deleteProductById(long id);
}
