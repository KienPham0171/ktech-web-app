package com.kein.ktech.repository.impl;

import com.kein.ktech.domain.Cart;
import com.kein.ktech.repository.CartRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CartRepositoryImpl implements CartRepository {
    @PersistenceContext
    EntityManager em;
    @Override
    public List<Cart> getCartByUserId(long userId) {
        String q = "select c from Cart c where c.userId.id = ?1";
        TypedQuery<Cart> result =  em.createQuery(q,Cart.class).setParameter(1,userId);
        return result.getResultList();
    }

    @Override
    public Optional<Cart> getCartIsNotCheckOutByUserId(long userId) {
        String q = "select c from Cart c where c.isCheckOut = false and c.userId.id =?1";
        TypedQuery<Cart> result = em.createQuery(q,Cart.class).setParameter(1,userId);
        return result.getResultList()
                .stream().findFirst();
    }

    @Override
    public void createNewCart(Cart cart) {
        em.persist(cart);
    }

    @Override
    public void updateCart(Cart cart) {
        em.merge(cart);
    }
}
