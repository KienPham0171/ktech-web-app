package com.kein.ktech.controller;

import com.kein.ktech.domain.RegisteredObj;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("registeredObj",new RegisteredObj());
        return "register";
    }
}
