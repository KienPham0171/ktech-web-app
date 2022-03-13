package com.kein.ktech.repository;

import com.kein.ktech.domain.Comment;
import com.kein.ktech.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentsByProduct(Product product);
}
