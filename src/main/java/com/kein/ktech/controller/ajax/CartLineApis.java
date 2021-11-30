package com.kein.ktech.controller.ajax;

import com.kein.ktech.domain.Cart;
import com.kein.ktech.domain.CartLine;
import com.kein.ktech.service.CartLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CartLineApis {
    CartLineService cartLineService;
    @Autowired
    public CartLineApis(CartLineService cartLineService) {
        this.cartLineService = cartLineService;
    }

    @PutMapping("/cartLines/{id}/{quantity}")
    public CartLine updateCartLine(@PathVariable int quantity, @PathVariable long id){
        CartLine cartLine = null;
        Optional<CartLine> c =  cartLineService.getCartLineById(id);
        if(!c.isPresent()) return null;
        else cartLine = c.get();
        cartLine.setQuantity(quantity);
        cartLineService.updateCartLine(cartLine);
        return cartLine;
    }
    @RequestMapping(value = "/cartLines/{id}",method = RequestMethod.DELETE)
    public String deleteCartline(@PathVariable long id){
        cartLineService.deleteCartline(id);
        return "Delete successfully";
    }

}
