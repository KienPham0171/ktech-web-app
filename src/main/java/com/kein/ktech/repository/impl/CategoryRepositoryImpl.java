package com.kein.ktech.repository.impl;

import com.kein.ktech.domain.Category;
import com.kein.ktech.domain.Product;
import com.kein.ktech.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<Category> getCategories() {
        String query = "select cat from Category cat";
        TypedQuery<Category> result = entityManager.createQuery(query,Category.class);
        return result.getResultList();
    }

    @Override
    public Optional<Category> getCategoryById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        String query = "Select c from Category c where c.name = ?1";
        TypedQuery<Category> result =entityManager.createQuery(query,Category.class).setParameter(1,name);
        return Optional.ofNullable(result.getSingleResult());
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return category.getProducts();
    }
}
