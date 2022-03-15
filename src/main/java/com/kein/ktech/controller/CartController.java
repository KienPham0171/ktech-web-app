package com.kein.ktech.controller;

import com.kein.ktech.domain.Address;
import com.kein.ktech.domain.Cart;
import com.kein.ktech.domain.CartLine;
import com.kein.ktech.domain.User;
import com.kein.ktech.security.CustomOauth2User;
import com.kein.ktech.security.CustomUserDetails;
import com.kein.ktech.service.CartService;
import com.kein.ktech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;
    @GetMapping("/cart")
    public String cart(Model model){
        User user = getUser(SecurityContextHolder.getContext().getAuthentication());
        long userId = user.getId();
        Cart cart = null ;
        Optional<Cart> opt =cartService.getCartIsNotCheckOutById(userId);
        if(opt.isPresent()) {
            cart = opt.get();
        } else {
            cart = new Cart();
            cart.setUserId(user);
            cartService.createNewCart(cart);
        }
//        List<CartLine> lines = cart.getCartLines();
//        System.out.println(lines.size());
//        cart.getCartLines().forEach(line-> System.out.println(line.getProduct().getProductName()
//            + line.getProductDetailsOptionName()));
        model.addAttribute("cart",cart);
        return "cart";
    }
    @GetMapping("/checkout")
    public String checkOut(Model model){
        User user = getUser(SecurityContextHolder.getContext().getAuthentication());
        Cart cart = null;
        Optional<Cart> opt =cartService.getCartIsNotCheckOutById(user.getId());
        if(opt.isPresent()) {
            cart = opt.get();
        } else {
            cart = new Cart();
            cart.setUserId(user);
            cartService.createNewCart(cart);
        };
        double invoiceTotal= cart.getCartLines().stream().mapToDouble(line -> line.getPrice() * line.getQuantity()).sum();
        List<Address> addresses = user.getAddresses();
        model.addAttribute("invoiceTotal",invoiceTotal);
        model.addAttribute("user",user);
        if(addresses.size()>0){
        model.addAttribute("addresses",addresses);}


        return "checkout";
    }
    public User getUser(Authentication authentication)
    {
        User u = null;
        var principal = authentication.getPrincipal();
        if(principal instanceof OAuth2User){
            CustomOauth2User user = new CustomOauth2User((OAuth2User)authentication.getPrincipal());
            return  userService.getUserByEmail(user.getEmail());

        }else{
            if(principal instanceof CustomUserDetails)
            {
                CustomUserDetails user = (CustomUserDetails) principal;
                return userService.getUserByEmail(user.getEmail());
            }
        }
        return null;
    }
}
