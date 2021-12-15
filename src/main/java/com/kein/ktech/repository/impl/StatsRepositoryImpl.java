package com.kein.ktech.repository.impl;

import com.kein.ktech.domain.Category;
import com.kein.ktech.domain.Product;
import com.kein.ktech.repository.StatsRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {
    @PersistenceContext
    EntityManager em;
    @Override
    public List<Object[]> catStats() {
        CriteriaBuilder b = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = b.createQuery(Object[].class);
        Root<Product> rootP = query.from(Product.class);
        Root<Category> rootC = query.from(Category.class);
        query.where(b.equal(rootP.get("categoryId"),rootC.get("id")));
        query.multiselect(rootC.get("id"),rootC.get("name"),
                b.count(rootP.get("id")));
        query.groupBy(rootC.get("id"));
        Query q = em.createQuery(query);
        return q.getResultList();
    }
}
