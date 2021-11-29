package com.kein.ktech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShoppingCartController {

    @RequestMapping("/shoppingCart")
    public String shoppingCart()
    {
        return "cart";
    }
}
