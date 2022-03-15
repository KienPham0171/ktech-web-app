package com.kein.ktech.service;

import com.kein.ktech.domain.Category;
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
    List<Product> getProductsByStatusAndSize(String status,int size);
    List<Product> getRelatedProductsByCategory(Category category);
    List<Product> getProductsBySizeAndDifferentCategory(int size,Category category,long id);

    void deleteProductById(long id);
}
