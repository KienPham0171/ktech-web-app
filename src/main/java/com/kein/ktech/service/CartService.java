package com.kein.ktech.service;

import com.kein.ktech.domain.Cart;

import java.util.List;
import java.util.Optional;

public interface CartService {
    Optional<Cart> getCartIsNotCheckOutById(long userId);
    List<Cart> getCartByUserId(long userId);

    void createNewCart(Cart cart);
    void updateCart(Cart cart);
}
