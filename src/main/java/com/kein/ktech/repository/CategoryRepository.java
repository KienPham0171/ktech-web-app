package com.kein.ktech.repository;

import com.kein.ktech.domain.Category;
import com.kein.ktech.domain.Product;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> getCategories();
    Optional<Category> getCategoryById(long id);
    Optional<Category> getCategoryByName(String name);
    List<Product> getProductsByCategory(Category category);
}
