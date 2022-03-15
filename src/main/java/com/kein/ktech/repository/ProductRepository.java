package com.kein.ktech.repository;

import com.kein.ktech.domain.Category;
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

    List<Product> getProductsByPageAndSize(int page, int size);
    List<Product> getProductsByStatusAndSize(String status, int size);
    List<Product> getRelatedProductsByCategory(Category category);
    List<Product> getProductsBySizeAndDifferentCategory(int size, Category category,long id);
    Product updateProduct(Product product);
    void delProductById(long id);
}
