package com.kein.ktech.service.impl;

import com.kein.ktech.domain.Product;
import com.kein.ktech.repository.ProductRepository;
import com.kein.ktech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository repository;
    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getProducts() {
        return repository.getProducts();
    }

    @Override
    public List<Product> getProductsByPageAndSize(int page, int size) {
        return this.repository.getProductsByPageAndSize(page, size);
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return repository.searchProductsByName(name);
    }

    @Override
    public Optional<Product> findProductById(long id) {
        return repository.findProductById(id);
    }

    @Override
    public void saveProduct(Product product) {
        repository.saveProduct(product);
    }

    @Override
    public long countProducts() {
        return this.repository.countProducts();
    }
}
