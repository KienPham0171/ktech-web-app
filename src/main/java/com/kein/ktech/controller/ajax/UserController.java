package com.kein.ktech.controller.ajax;

import com.kein.ktech.constant.AuthProvider;
import com.kein.ktech.domain.RegisteredObj;
import com.kein.ktech.domain.User;
import com.kein.ktech.repository.UserRepository;
import com.kein.ktech.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/new-user")
    public String newUser(@ModelAttribute(name ="registeredObj")RegisteredObj registeredObj, Model model){

        if(checkUser(registeredObj)){
            String generatedString = RandomStringUtils.randomAlphabetic(32);
            User newUser = new User();
            newUser.setEmail(registeredObj.getEmail());
            newUser.setPassword(encoder.encode(registeredObj.getPassword()));
            newUser.setFullName(registeredObj.getName());
            newUser.setActive(false);
            newUser.setAuthProvider(AuthProvider.LOCAL);
            newUser.setVerificationCode(generatedString);
            try{
                userService.createNewUser(newUser);
            }catch(Exception e){
                System.err.println("Occur error while creating new user");
            }
            return "login";
        }else{
            model.addAttribute("registeredObj",registeredObj);
            model.addAttribute("registerError","This email has been used for another user!");
            return "register";
        }
    }

    private boolean checkUser(RegisteredObj obj){
        if(!Objects.equals(obj.getPassword(), obj.getConfirmPassword()))return false;
        return userService.findUserByEmail(obj.getEmail()).isEmpty();
    }
}
