package com.kein.ktech.repository;

import com.kein.ktech.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getProducts();
    Product getProductById(long id);
    List<Product> searchProductsByName(String name);

    void saveProduct(Product product);

    Optional<Product> findProductById(long id);
    long countProducts();
}
