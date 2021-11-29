package com.kein.ktech.security.handler;

import com.kein.ktech.constant.AuthProvider;
import com.kein.ktech.domain.User;
import com.kein.ktech.repository.UserRepository;
import com.kein.ktech.security.CustomOauth2User;
import com.kein.ktech.security.CustomOauth2UserServices;
import com.kein.ktech.security.CustomUserDetails;
import com.kein.ktech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {
    private UserService service;



    @Autowired
    public Oauth2SuccessHandler(UserService service) {
        this.service = service;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User o = (OAuth2User) authentication.getPrincipal();
        CustomOauth2User user = new CustomOauth2User(o);
        String email = user.getEmail();
        User result = service.getUserByEmail(email);
        if(result != null)
        {
            System.out.println("result != null ");
            response.sendRedirect("/home");
        }else
        {
            System.out.println("result == null");
            var st = new User(user.getEmail(),user.getName(),true, AuthProvider.OAUTH2);
            service.createNewUser(st);
            response.sendRedirect("/home");
        }


    }
}
