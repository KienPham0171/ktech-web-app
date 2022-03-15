package com.kein.ktech.controller;

import com.kein.ktech.domain.Category;
import com.kein.ktech.domain.Product;
import com.kein.ktech.domain.Role;
import com.kein.ktech.domain.User;
import com.kein.ktech.security.CustomOauth2User;
import com.kein.ktech.security.CustomUserDetails;
import com.kein.ktech.service.CategoryService;
import com.kein.ktech.service.ProductService;
import com.kein.ktech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@ControllerAdvice
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService catService;
    @Autowired
    ProductService productService;

    @ModelAttribute(name = "userName")
    public String userName() {
        SecurityContext context = SecurityContextHolder.getContext();
        if(getPrincipal(context.getAuthentication())!=null ){
            String userName = getPrincipal(context.getAuthentication());
            if(!userName.contains(" ")) return userName;
            return  userName.substring(userName.lastIndexOf(" ",userName.length()-1));

        }
        return getPrincipal(context.getAuthentication());
    }
    @ModelAttribute(name="objUser")
    public User objUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        return getUser(context.getAuthentication());
    }
    @ModelAttribute(name="isAdmin")
    public boolean isAdmin(){
        User user = getUser(SecurityContextHolder.getContext().getAuthentication());
        if(user != null){
            Collection<Role> roles =user.getRoles();
            for(Role r : roles){
                if(r.getRoleName().equalsIgnoreCase("admin")) return true;
            }
        }

        return false;
    }

    @ModelAttribute(name="cats")
    public List<Category> getCats(){
        return catService.getCategories();
    }

    @GetMapping("/")
    public String welcome(Model model)
    {
        model.addAttribute("cats",catService.getCategories());
        List<Product> listNew = productService.getProductsByStatusAndSize("isNew",5);
        model.addAttribute("listNew",listNew);
        return "home1";
    }
    @GetMapping("/home")
    public String home(Authentication authentication, Model model)
    {
        model.addAttribute("cats",catService.getCategories());
        List<Product> listNew = productService.getProductsByStatusAndSize("isNew",5);
        model.addAttribute("listNew",listNew);
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


    public User getUser(Authentication authentication)
    {
        User u;
        var principal = authentication.getPrincipal();
        if(principal instanceof OAuth2User){
            CustomOauth2User user = new CustomOauth2User((OAuth2User)authentication.getPrincipal());
            return  userService.getUserByEmail(user.getEmail());

        }else{
            if(principal instanceof  CustomUserDetails)
            {
                CustomUserDetails user = (CustomUserDetails) principal;
                return userService.getUserByEmail(user.getEmail());
            }
        }
        return null;
    }
}
