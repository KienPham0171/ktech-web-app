package com.kein.ktech.controller;

import com.kein.ktech.domain.Address;
import com.kein.ktech.domain.User;
import com.kein.ktech.security.CustomOauth2User;
import com.kein.ktech.security.CustomUserDetails;
import com.kein.ktech.service.AddressService;

import com.kein.ktech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AddressController {

    @Autowired
    AddressService addressService;
    @Autowired
    UserService userService;
    @PostMapping("/addresses")
    public String createAddress(@RequestParam(name = "newAdd") String address, Model model, Authentication authentication){
        String notice= null ;
        if(address.trim().equals("")){
            notice = "Invalid address!";
        }else{
            notice= "Add address successfully!";
        }
        model.addAttribute("notice", notice);

        User u = null;
        var principal = authentication.getPrincipal();
        if(principal instanceof OAuth2User){
            CustomOauth2User user = new CustomOauth2User((OAuth2User)authentication.getPrincipal());
            u =   userService.getUserByEmail(user.getEmail());

        }else{
            if(principal instanceof CustomUserDetails)
            {
                CustomUserDetails user = (CustomUserDetails) principal;
                u = userService.getUserByEmail(user.getEmail());
            }
        }
        addressService.createAddress(new Address(address,u));
        return "redirect:/checkout";
    }
}
