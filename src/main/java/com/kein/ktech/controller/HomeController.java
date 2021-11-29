package com.kein.ktech.controller;

import com.kein.ktech.security.CustomOauth2User;
import com.kein.ktech.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@ControllerAdvice
public class HomeController {

    @ModelAttribute(name = "userName")
    public String userName() {
        SecurityContext context = SecurityContextHolder.getContext();

        return getPrincipal(context.getAuthentication());
    }
    @GetMapping("/home")
    public String home(Authentication authentication, Model model)
    {
        return "home1";
    }
    public String getPrincipal(Authentication authentication)
    {
        String userName;
        var principal = authentication.getPrincipal();
        if(principal instanceof OAuth2User){
            CustomOauth2User user = new CustomOauth2User((OAuth2User)authentication.getPrincipal());
            System.out.println("log by oauth 2 name: " + user.getName() );
            userName = user.getName();
            return userName;
        }else{
            if(principal instanceof  CustomUserDetails)
            {
                CustomUserDetails user = (CustomUserDetails) principal;
                System.out.println("log by regular user: "+ user.getUsername());
                userName = user.getFullName();
                return userName;
            }

//            CustomUserDetails user = (CustomUserDetails) principal;
//            System.out.println("log by regular user: "+ user.getUsername());
//            userName = user.getUsername();
        }
        return null;
    }
}
