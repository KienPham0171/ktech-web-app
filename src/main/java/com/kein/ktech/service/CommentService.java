package com.kein.ktech.service;

import com.kein.ktech.domain.Comment;
import com.kein.ktech.domain.Product;

public interface CommentService {
    Comment createComment(Comment comment);
    void removeCommentByProduct(Product product);
}
