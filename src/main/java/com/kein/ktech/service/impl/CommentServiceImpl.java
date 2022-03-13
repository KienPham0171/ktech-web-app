package com.kein.ktech.service.impl;

import com.kein.ktech.domain.Comment;
import com.kein.ktech.domain.Product;
import com.kein.ktech.repository.CommentRepository;
import com.kein.ktech.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    CommentRepository repository;
    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }
    @Override
    public Comment createComment(Comment comment){
        return repository.save(comment);
    }

    @Override
    public void removeCommentByProduct(Product product) {
        this.repository.deleteCommentsByProduct(product);
    }

}
