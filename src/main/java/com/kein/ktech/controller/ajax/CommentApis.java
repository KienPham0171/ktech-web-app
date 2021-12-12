package com.kein.ktech.controller.ajax;

import com.kein.ktech.domain.Comment;
import com.kein.ktech.domain.Product;
import com.kein.ktech.domain.User;
import com.kein.ktech.service.CommentService;
import com.kein.ktech.service.ProductService;
import com.kein.ktech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CommentApis {
    UserService userService;
    ProductService productService;
    CommentService commentService;

    @Autowired
    public CommentApis(UserService userService, ProductService productService, CommentService commentService) {
        this.userService = userService;
        this.productService = productService;
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public Comment createComment(@RequestBody Comment comment){

        long userId = comment.getUserId();
        long productId = comment.getProductId();
        User user = userService.findUserById(userId);
        Optional<Product> p = productService.findProductById(productId);
        Product product = null;
        if(p.isPresent()) product = p.get();
        comment.setUser(user); comment.setProduct(product);
        Calendar cld = Calendar.getInstance();
        Date date =  cld.getTime();
        comment.setCreatedDate(date);
        return commentService.createComment(comment);

    }
}
