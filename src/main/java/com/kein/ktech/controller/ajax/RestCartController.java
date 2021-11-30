package com.kein.ktech.controller.ajax;

import com.kein.ktech.domain.Cart;
import com.kein.ktech.domain.CartLine;
import com.kein.ktech.domain.Product;
import com.kein.ktech.domain.User;
import com.kein.ktech.service.CartLineService;
import com.kein.ktech.service.CartService;
import com.kein.ktech.service.ProductService;
import com.kein.ktech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RestCartController {
    UserService userService;
    ProductService productService;
    CartService cartService;
    CartLineService cartLineService;
    @Autowired
    public RestCartController(UserService userService, ProductService productService,
                              CartService cartService,CartLineService cartLineService) {
        this.userService = userService;
        this.productService = productService;
        this.cartService = cartService;
        this.cartLineService = cartLineService;
    }

    @RequestMapping(value = "/cartline",method = RequestMethod.POST)
    public String addItem(ServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        data = data.substring(1,data.length()-1).trim();
        data=data.replace(" ",""); data =data.replace("\"","");
        data = data.replace(":",",");
        String[] st = data.split(",");

        long userId = Long.parseLong(st[1]);
        long productId = Long.parseLong(st[3]);
        String optionName = st[5];
        double price = Double.parseDouble(st[7]);
        User user = userService.findUserById(userId);
        Product product;
        Optional<Product> p = productService.findProductById(productId);
        if (p.isPresent())product = p.get();
        else return "Add failure";
        Cart cart ;
        Optional<Cart> opt =cartService.getCartIsNotCheckOutById(userId);
        if(opt.isPresent()) {
            cart = opt.get();
        } else {
            cart = new Cart();
            cart.setUserId(user);
            cartService.createNewCart(cart);
        }
        List<CartLine> cartLines =  cart.getCartLines();
        for (CartLine cartLine : cartLines){
            if(cartLine.getProduct().getId() == productId
                    && cartLine.getProductDetailsOptionName().compareTo(optionName)==0){
               cartLine.setQuantity(cartLine.getQuantity()+1);
               cartLineService.updateCartLine(cartLine);
               return "Update Successfully";
            }
        }
        CartLine cartLine = new CartLine();
        cartLine.setCart(cart);
        cartLine.setPrice(price);
        cartLine.setProduct(product);
        cartLine.setProductDetailsOptionName(optionName);
        cartLine.setQuantity(1);
        cartLineService.createCartLine(cartLine);


        return "Add Successfully";
    }
    @GetMapping("/cartline")
    public String sayHello(){
        return "hello";
    }
}
