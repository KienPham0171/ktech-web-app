package com.kein.ktech.repository;

import com.kein.ktech.domain.User;

import java.util.List;


public interface UserRepository {

    List<User> findAll();
    void createNewUser(User user);
    User findById(Long id);
    String findUserVerificationCodeById(String email);

    boolean enableUser(User user);
}
