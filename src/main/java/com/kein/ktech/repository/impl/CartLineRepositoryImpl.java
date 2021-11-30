package com.kein.ktech.repository.impl;

import com.kein.ktech.domain.CartLine;
import com.kein.ktech.repository.CartLineRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
@Transactional
public class CartLineRepositoryImpl implements CartLineRepository {
    @PersistenceContext
    EntityManager em;
    @Override
    public void createCartLine(CartLine cartLine) {
        em.persist(cartLine);
    }

    @Override
    public Optional<CartLine> getCartLineById(long id) {
        String query = "select c from CartLine c where c.id = ?1";
        TypedQuery<CartLine> q = em.createQuery(query,CartLine.class).setParameter(1,id);
        return q.getResultList().stream().findFirst();
    }

    @Override
    public void updateCartLine(CartLine cartLine) {
        em.merge(cartLine);
    }

    @Override
    public void deleteCartline(long id) {
        Optional<CartLine> c = getCartLineById(id);
        c.ifPresent(cartLine -> em.remove(cartLine));
    }
}
