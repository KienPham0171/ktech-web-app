package com.kein.ktech.repository.impl;

import com.kein.ktech.domain.User;
import com.kein.ktech.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
   // private final EntityManagerFactory factory;

    @PersistenceContext
    private EntityManager em;




    @Override
    public void createNewUser(User user) {
        em.persist(user);
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public String findUserVerificationCodeById(String email) {
        /*CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root)
                .where(cb.equal( root.get("email"),cb.parameter(String.class,email) ));*/
        String q = "SELECT u FROM User u Where u.email='"+email+"'";
        TypedQuery<User> result = em.createQuery(q,User.class);
        User user = result.getSingleResult();
        return user.getVerificationCode();

    }

    @Override
    public boolean enableUser(User user) {
        User managedUser = this.findById(user.getId());
        user.setActive(true);
        return true;
    }

    @Override
    public List<User> findAll() {
        //TypedQuery<User> query = em.createQuery("SELECT e FROM User e", User.class);
        CriteriaBuilder buider = em.getCriteriaBuilder();
        CriteriaQuery<User> q = buider.createQuery(User.class);
        Root<User> u = q.from(User.class);
        q.select(u);
        TypedQuery<User> result = em.createQuery(q);

        return result.getResultList();
    }


}
