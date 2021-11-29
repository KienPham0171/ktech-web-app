package com.kein.ktech.service.impl;

import com.kein.ktech.domain.Category;
import com.kein.ktech.domain.Product;
import com.kein.ktech.repository.CategoryRepository;
import com.kein.ktech.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository repository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getCategories() {
        return repository.getCategories();
    }

    @Override
    public Optional<Category> getCategoryById(long id) {
        return (Optional<Category>) repository.getCategoryById(id);
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return repository.getCategoryByName(name);
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return repository.getProductsByCategory(category);
    }
}
