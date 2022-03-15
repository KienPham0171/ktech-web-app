package com.kein.ktech.repository.impl;

import com.kein.ktech.domain.Category;
import com.kein.ktech.domain.Product;
import com.kein.ktech.repository.ProductRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<Product> getProducts() {
        String query  = "SELECT p FROM Product p";
        TypedQuery<Product> q = entityManager.createQuery(query,Product.class);
        return q.getResultList();
    }

    @Override
    public Product getProductById(long id) {
        return null;
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        String query  = "SELECT p FROM Product p WHERE p.productName LIKE :name";
        TypedQuery<Product> q = entityManager.createQuery(query,Product.class).setParameter("name","%"+name+"%");
        return q.getResultList();
    }

    @Override
    public void saveProduct(Product product) {
         entityManager.persist(product);
    }

    @Override
    public Optional<Product> findProductById(long id) {
        String query = "select p from Product p where p.id = ?1";
        TypedQuery<Product> result = entityManager.createQuery(query,Product.class).setParameter(1,id);
        return Optional.ofNullable(result.getSingleResult());
    }

    @Override
    public long countProducts() {
        String query = "select count(p.id) from Product p";
        return Long.parseLong(entityManager.createQuery(query).getSingleResult().toString());
    }

    @Override
    public List<Product> getProductsByPageAndSize(int page, int size) {
        String query = "select p from Product p ";
        TypedQuery<Product> q = entityManager.createQuery(query,Product.class)
                .setMaxResults(size).setFirstResult((page -1)*size);
        return q.getResultList();
    }

    @Override
    public List<Product> getProductsByStatusAndSize(String status, int size) {
        //if size = -1 get all products
        String q = "select p from Product p where p.status = ?1";
        TypedQuery<Product> result;
        if(size !=-1){
            result = entityManager.createQuery(q,Product.class)
                    .setParameter(1,status).setMaxResults(size).setFirstResult(0);

        }else{
            result = entityManager.createQuery(q,Product.class).setParameter(1,status);
        }
        return result.getResultList();
    }

    @Override
    public List<Product> getRelatedProductsByCategory(Category category) {
        String q = "select p from Product p where p.categoryId = ?1";
        TypedQuery<Product> result = entityManager.createQuery(q,Product.class)
                .setParameter(1,category).setFirstResult(0).setMaxResults(8);
        return result.getResultList();
    }

    @Override
    public List<Product> getProductsBySizeAndDifferentCategory(int size,Category category,long id) {
        String q = "select p from Product p where p.categoryId <> ?1 and p.id <>?2";
        TypedQuery<Product> result = entityManager.createQuery(q,Product.class)
                .setParameter(1,category).setParameter(2,id)
                .setFirstResult(0).setMaxResults(size);
        return result.getResultList();
    }

    @Override
    public Product updateProduct(Product product) {
        return entityManager.merge(product);
    }

    @Override
    public void delProductById(long id) {
        Optional<Product> product = findProductById(id);
        product.ifPresent(value -> entityManager.remove(value));
    }
}
