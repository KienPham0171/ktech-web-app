package com.kein.ktech.service;

import com.kein.ktech.domain.User;
import com.kein.ktech.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getUsers();
    User findUserById(Long id);
    User getUserByEmail(String email);;
    void createNewUser(User user);
    void sendEmailVerification(CustomUserDetails user);

    boolean enableUser(User user);
}
