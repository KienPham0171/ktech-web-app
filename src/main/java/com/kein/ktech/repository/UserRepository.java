package com.kein.ktech.repository;

import com.kein.ktech.domain.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository {

    List<User> findAll();
    void createNewUser(User user);
    User findById(Long id);
    String findUserVerificationCodeById(String email);
    Optional<User> findUserByEmail(String email);

    boolean enableUser(User user);
}
